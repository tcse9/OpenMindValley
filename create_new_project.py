# to run this project run the following command in terminal:
# python3 create_new_project.py NewProjectName com.newproject.package.name
import os
import subprocess
import sys
from pathlib import Path

# Configuration - Update these as per your boilerplate repo and package
BOILERPLATE_REPO = "https://github.com/tcse9/OpenMindValley.git"  # Replace with your GitHub boilerplate repo
OLD_PACKAGE_NAME = "com.openmindvalley.android.app"  # Original package name in your boilerplate

def run_command(command):
    """Run a shell command and check for errors."""
    result = subprocess.run(command, shell=True, check=True, text=True, capture_output=True)
    if result.returncode != 0:
        print(f"Error: {result.stderr}")
        sys.exit(1)
    return result.stdout

def clone_boilerplate(new_project_name):
    """Clone the boilerplate repository into a new directory."""
    print(f"Cloning boilerplate repository into '{new_project_name}'...")
    run_command(f"git clone {BOILERPLATE_REPO} {new_project_name}")
    print("Repository cloned successfully.")

def replace_in_files(directory, old_text, new_text):
    """Recursively replace text in all relevant files."""
    for file in directory.rglob("*"):
        if file.is_file() and file.suffix in {".kt", ".java", ".xml", ".gradle", ".pro"}:
            content = file.read_text()
            content = content.replace(old_text, new_text)
            file.write_text(content)

def rename_package_directories(base_path, old_package_name, new_package_name):
    """Rename package directories to match the new package structure."""
    old_package_path = Path(base_path, *old_package_name.split('.'))
    new_package_path = Path(base_path, *new_package_name.split('.'))
    
    if old_package_path.exists():
        new_package_path.parent.mkdir(parents=True, exist_ok=True)
        old_package_path.rename(new_package_path)

def update_project(new_project_name, new_package_name):
    """Update the project name and package name throughout the project."""
    new_project_path = Path(new_project_name)
    print("Replacing package name in files...")
    replace_in_files(new_project_path, OLD_PACKAGE_NAME, new_package_name)
    print("Package name replaced.")

    print("Renaming package directories...")
    rename_package_directories(new_project_path / "app/src/main/java", OLD_PACKAGE_NAME, new_package_name)
    print("Package directories renamed.")

    print("Updating app name in resources...")
    strings_file = new_project_path / "app/src/main/res/values/strings.xml"
    if strings_file.exists():
        content = strings_file.read_text()
        content = content.replace("BoilerplateApp", new_project_name)
        strings_file.write_text(content)
    print("App name updated in resources.")

def main(new_project_name, new_package_name):
    if not new_project_name or not new_package_name:
        print("Usage: python create_new_project.py <new_project_name> <new_package_name>")
        sys.exit(1)

    clone_boilerplate(new_project_name)
    update_project(new_project_name, new_package_name)
    print(f"New project '{new_project_name}' created with package name '{new_package_name}'.")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python create_new_project.py <new_project_name> <new_package_name>")
        sys.exit(1)
    new_project_name = sys.argv[1]
    new_package_name = sys.argv[2]
    main(new_project_name, new_package_name)
