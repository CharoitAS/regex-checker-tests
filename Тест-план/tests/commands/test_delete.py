# Команда delete
def test_1(run_cli):
  result = run_cli(["create abc yummi", "yummi", "delete yummi"])  
  assert result.errors == ""
  assert "The object [abc] yummi was created" in result.output
  assert "The object [abc] yummi was deleted" in result.output

# Команда delete regex
def test_2(run_cli):
  result = run_cli(["create regex Q", "cammila", "delete Q"])  
  assert result.errors == ""
  assert "The object [regex] Q was created" in result.output
  assert "The object [regex] Q was deleted" in result.output

# Команда delete существующего объекта 
def test_3(run_cli):
  result = run_cli("delete ab")  
  assert result.errors == ""
  assert result.output == "The object [abc] ab was deleted"

# Команда delete два объекта
def test_4(run_cli):
  result = run_cli(["create abc rr", "ri", "create regex W", "ru", "delete rr W"]) 
  assert result.errors == ""
  assert "The object [abc] rr was created" in result.output
  assert "The object [regex] W was created" in result.output
  assert "The object [abc] rr was deleted" in result.output
  assert "The object [regex] W was deleted" in result.output