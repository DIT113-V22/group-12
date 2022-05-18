extends Reference

var mod_name: String = "careship"

func init(global) -> void:
	global.register_environment("careship/Careship", load("res://src/environments/careship/Careship.tscn"))
	print("Hello World!")
