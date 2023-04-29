# Программа завершается после ввода команды "quit", ничего не печатая на экран и не возвращая код ошибки
def test_1(run_cli):
  result = run_cli("quit")  
  assert result.errors == ""
  assert result.output == ""

# Любые команды после "quit" игнорируются (в том числе заведомо бессмысленная "s@me_eRroneous_comm@nd")
def test_2(run_cli):
  result = run_cli(["quit", "s@me_eRroneous_comm@nd"])  
  assert result.errors == ""
  assert result.output == ""

# Без команды "quit" заведомо бессмысленная команда "s@me_eRroneous_comm@nd" привела бы к аварийному завершению программы
def test_3(run_cli):
  result = run_cli("s@me_eRroneous_comm@nd", fail_allowed = True)  
  assert result.errors != ""
