# Bitrix Controller Navigator Plugin

A PHPStorm plugin that enables seamless navigation between JavaScript Bitrix ajax calls and their corresponding PHP controller files. Navigate from `BX.ajax.runAction` calls directly to controller implementations with a simple Ctrl+Click.

[![Release](https://img.shields.io/badge/release-v.1.0.0-blue.svg)](https://github.com/yognevoy/bx-controller-navigator-plugin/releases/tag/v1.0.0)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/yognevoy/bx-controller-navigator-plugin/blob/master/LICENSE.txt)
![JetBrains Plugin Downloads](https://img.shields.io/jetbrains/plugin/d/27457-bitrix-controller-navigator)

## Features

The Bitrix Controller Navigator plugin enhances PHPStorm's developer experience for Bitrix projects:

**Controller Navigation** - Ctrl+Click on controller paths in JavaScript ajax calls to jump directly to the corresponding PHP controller file or method.

**Intelligent Autocompletion** - Get context-aware suggestions for available controller paths.

**Namespace Resolution** - Automatically resolves controller locations based on Bitrix module namespace configurations in .settings.php files.

**Path Format Recognition** - Understands and processes Bitrix controller path format `vendor:module.namespace.controller.action`.

## Getting started

### Installation

**From JetBrains Marketplace**

1. Visit the [Bitrix Controller Navigator](https://plugins.jetbrains.com/plugin/27457-bitrix-controller-navigator/) in Marketplace
2. Click "Get" and start IDE when prompted

**Manual Installation**

1. Download the plugin zip file from Releases 
2. Open PHPStorm 
3. Go to Settings → Plugins 
4. Click the gear icon and select "Install Plugin from Disk..."
5. Select the downloaded .zip file 
6. Restart PHPStorm

### Requirements

- [x] PHPStorm 2022.2 or newer

### Configuration

The plugin requires minimal configuration. Go to Settings → Tools → Bitrix Controller Navigator, and set the path to your local Bitrix directory (`/public/www/` by default). Apply changes and enjoy enhanced navigation

## Usage

### Controller Navigation 

Place your cursor on a controller path string in a `BX.ajax.runAction` call, then press Ctrl+Click (or Cmd+Click on macOS). PHPStorm will navigate directly to the corresponding controller file or specific action method.

```javascript
// Example: Ctrl+Click on the controller path will navigate to the corresponding PHP file
BX.ajax.runAction('vendor:module.controller.namespace.action');
```

### Autocompletion

Begin typing a controller path and press Ctrl+Space to see available controller paths based on your project structure. Select the desired controller from the suggestions to insert the full path.

## How It Works

The plugin works by:

1. Identifying controller path strings in `BX.ajax.runAction` calls 
2. Parsing the controller path into vendor, module, controller, and action components 
3. Searching the project for .settings.php files to locate the appropriate module 
4. Using the namespace configuration in .settings.php to determine the controller's location 
5. Providing references to the specific controller file or method

## How to Contribute

If you find a bug or have a feature request, please check the [Issues page](https://github.com/yognevoy/bx-controller-navigator-plugin/issues) before creating a new one. For code contributions, fork the repository, make your changes on a new branch, and submit a pull request with a clear description of the changes. Please make sure to test your changes thoroughly before submitting.

## License
This project is licensed under the MIT License - see the [LICENSE.txt](https://github.com/yognevoy/bx-controller-navigator-plugin/blob/master/LICENSE.txt) file for details.
