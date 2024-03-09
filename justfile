_default:
	@just --list

run:
	gradle run

update_eclipse:
	# Update .classpath and eclipse related files. (for eclipse.jdt.ls support)
	gradle cleanEclipse eclipse

lint_all:
	pre-commit run --all-files

todo:
	rg ".(FIX|TODO|HACK|WARN|PREF|NOTE): " --glob !{{ file_name(justfile()) }}

# vim: set ft=make :
