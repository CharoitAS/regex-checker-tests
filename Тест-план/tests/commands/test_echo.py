# echo выводит текст написанный после команды
def test_1(run_cli):
  result = run_cli("echo empty")  
  assert result.errors == ""
  assert result.output == "empty"

# echo может выводить текст не из ASCII кодировки
def test_2(run_cli):
  result = run_cli("echo öÿÑà한国のф")  
  assert result.errors == ""
  assert result.output == "öÿÑà한国のф"

# echo может выводить спецсимволы
def test_3(run_cli):
  result = run_cli("echo `~@#$%^&*()_+|-=\\{}[]:”;’<>?,./®©£¥¢¦§«»€")  
  assert result.errors == ""
  assert result.output == "`~@#$%^&*()_+|-=\\{}[]:”;’<>?,./®©£¥¢¦§«»€"

# echo может выводить текст из 256 символов
def test_4(run_cli):
  result = run_cli("echo 0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef")  
  assert result.errors == ""
  assert result.output == "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef"

# Команда echo без текста
def test_5(run_cli):
  result = run_cli("echo")  
  assert result.errors == ""
  assert result.output == ""

  # Использование множественных пробелов 
def test_6(run_cli):
  result = run_cli("echo vip   vip   ")  
  assert result.errors == ""
  assert result.output == "vip   vip   "