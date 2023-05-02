import pytest
import subprocess
from pathlib import Path
import sys
import os

# Represents results of the "run_cli" call
class Result:
  retcode: int  # error code, returned by the process. Equals to 0 on success
  output:  str  # stdout of the process as a string
  errors:  str  # stderr of the process as a string

def pytest_addoption(parser):
  parser.addoption("--cli", default=None, action="store")

def run_cli_implementation(cli_path, test, options=[], fail_allowed=False):
  command = [cli_path]

  if options:
    command.extend(options)

  if isinstance(test, list):
    test = "\n".join(test)
  test += "\n"

  run_results = subprocess.run(command, input=test.encode(), capture_output = True, timeout = 20)

  result = Result()
  result.retcode = run_results.returncode 
  result.output = run_results.stdout.decode()
  result.errors = run_results.stderr.decode()

  if result.errors != "":
    print(result.errors, file=sys.stderr)

  if result.output != "":
    print(result.output, file=sys.stdout)
  
  if result.errors.endswith("\n"):
    result.errors = result.errors[:-1].rstrip("\r")
  
  if result.output.endswith("\n"):
    result.output = result.output[:-1].rstrip("\r")
  
  if not fail_allowed:
    assert result.retcode == 0, "Return code of the program is not 0"

  return result

def find_cli():
  cli_name = "cli.exe" if os.name == "nt" else "cli"
  default_paths = [".", "dir_build", "build", "bin", "..", "../dir_build", "../build", "../bin"]
  for path in default_paths:
    cli_path = Path(path).joinpath(cli_name).absolute()
    if cli_path.is_file():
      return cli_path

  return None

@pytest.fixture
def run_cli(request):
  suggested_path = request.config.getoption("--cli")
  if suggested_path is not None:
    cli_path = Path(suggested_path)
    assert cli_path.is_file(), "Provided --cli option is not a valid path: {}".format(str(cli_path))
  else:
    cli_path = find_cli()
  assert cli_path is not None, "Could not autodetect location of 'cli'. Try --cli option"

  return lambda test, options=[], fail_allowed=False: run_cli_implementation(str(cli_path), test, options, fail_allowed=fail_allowed)
