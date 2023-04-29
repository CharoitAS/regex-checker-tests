# Команда "help" печатает на экран информацию о доступных командах - как минимум информацию о самой себе
def test_1(run_cli):
  result = run_cli("help")  
  assert result.errors == ""
  assert "help - show the list of commands" in result.output

# Если запустить программу с опцией "--ru", то команда "help" будет печатать текст на русском языке
def test_2(run_cli):
  result = run_cli("help", options=["--ru"])  
  assert result.errors == ""
  assert "help - выводит список поддерживаемых команд" in result.output

# Если ввести команду "lang RU", то команда "help" будет печатать текст на русском языке
def test_3(run_cli):
  result = run_cli(["lang RU", "help"])  
  assert result.errors == ""
  assert "help - выводит список поддерживаемых команд" in result.output

# Если запустить программу с опцией "--help", то она напечатает список доступных опций в stderr
#FIXME: обычно опции принято печатать в stdout. Наверное, надо будет потом исправить поведение программы
def test_4(run_cli):
  result = run_cli("", options=["--help"])  
  assert result.errors == "Usage: ./cli [--ru] [--[no-]fancy] [--[no-]fail-on-error] [--help]"
  assert result.output == ""
