import pytest

# Команда create abc
@pytest.mark.parametrize("value", ["yummi", "qss" , "murk", "123456789"])
def test_1(run_cli, value):
  result = run_cli(["create abc yummi", value])  
  assert result.errors == ""
  assert result.output == "The object [abc] yummi was created"

# Команда create regex
@pytest.mark.parametrize("value", ["cammila", "(aaa|b*a)*", "(asdas*dsa*ds*fsa)"])
def test_2(run_cli, value):
  result = run_cli(["create regex a", value])  
  assert result.errors == ""
  assert result.output == "The object [regex] a was created"

# Команда create regex
def test_3(run_cli):
  result = run_cli(["create regex a", ")EMPTY("])  
  assert result.errors == "Warning: Could not autodetect the alphabet for the empty regular expression. Using a"
  assert result.output == "The object [regex] a was created"

# Команда create regex
def test_4(run_cli):
  result = run_cli(["create regex a", ""])  
  assert result.errors == "Warning: Could not autodetect the alphabet for the empty regular expression. Using a"
  assert result.output == "The object [regex] a was created"  

# Команда create abc from
def test_5(run_cli):
  result = run_cli(["create abc A", "qwe", "create abc B from A"])  
  assert result.errors == ""
  assert "The object [abc] A was created" in result.output
  assert "The object [abc] B was created" in result.output

# Команда create regex from
def test_6(run_cli):
  result = run_cli(["create regex A", "qwe", "create regex B from A"])  
  assert result.errors == ""
  assert "The object [regex] A was created" in result.output
  assert "The object [regex] B was created" in result.output

# Команда create abc from regex
def test_7(run_cli):
  result = run_cli(["create regex A", "qwe", "create abc B from A"])  
  assert result.errors == ""
  assert "The object [regex] A was created" in result.output
  assert "The object [abc] B was created" in result.output