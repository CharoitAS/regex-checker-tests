# Команда create abc
def test_1(run_cli):
  result = run_cli(["create abc yummi", "yummi"])  
  assert result.errors == ""
  assert result.output == "The object [abc] yummi was created"

# Команда create regex
def test_2(run_cli):
  result = run_cli(["create regex a", "cammila"])  
  assert result.errors == ""
  assert result.output == "The object [regex] a was created"

# Команда create regex
def test_3(run_cli):
  result = run_cli(["create regex a", "(aaa|b*a)*"])  
  assert result.errors == ""
  assert result.output == "The object [regex] a was created"

# Команда create regex
def test_4(run_cli):
  result = run_cli(["create regex a", ")EMPTY("])  
  assert result.errors == "Warning: Could not autodetect the alphabet for the empty regular expression. Using a"
  assert result.output == "The object [regex] a was created"

# Команда create regex
def test_5(run_cli):
  result = run_cli(["create regex a", ""])  
  assert result.errors == "Warning: Could not autodetect the alphabet for the empty regular expression. Using a"
  assert result.output == "The object [regex] a was created"  