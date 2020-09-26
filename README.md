# TeamChat
TeamChat is a BungeeCord Plugin that gives you a private Chat only for your Staff Members.

## Getting started
###### Download
Download the <a href="https://github.com/Flurex/TeamChat/releases/latest">latest release</a> and upload the jar file to the plugins folder of your BungeeCord Server.

###### Start the Server
Start your BungeeCord Server.

###### Customize everything
You can customize every message, every permission and much more in the config files of the plugin.

###### Restart/Reload
Now you have to either restart the Server or type "/tc reload" in the Chat to reload the config files.

###### Have Fun!
Yes, the installation is that easy! If you have any problems please feel free to contact me through <a href="https://github.com/Flurex/TeamChat/issues">Github Issues</a>.

## Features
1. **Login/Logout System**: You can login/logout of the TeamChat with a command.
2. **GhostMode**: If you activate the GhostMode, no one can see that you are logged in to the TeamChat.
3. **Multilanguage**: You can choose between English and German.
4. **TeamChat Symbol**: You can choose a Symbol that you want to use insted of "/tc <Message>" to send a message in the TeamChat.
5. **Toggle**: You can toggle the TeamChat. That means that you can activate a mode where everything you write in the Chat is automatically sent into the TeamChat.
6. **List**: With a simple command, you can list everyone who is currently logged in to the TeamChat. Except for the people who have GhostMode activated.
7. **Update Checker**: You will automatically get a message if an update for the Plugin is available. Alternatively, you can disable this option in the config.yml.

## Configuration
This Plugin is completely customizable by editing the config files.
The following example is out of the config.yml.
```# Select your language:
# We currently support English (en) and German (de)
language: 'en'

# Are we allowed to automatically check for updates?
check_for_updates: true

# You can use this Symbol in front of your message insted of /tc to send a message into the TeamChat:
chat_symbol: '!'

# Permissions
# With this permission, a user is able to use the main functions of the TeamChat - /tc, /tc login, /tc logout
permission_use_teamchat: 'tc.use'

# With this permission, a user can list everyone who is currently logged in to the TeamChat:
permission_list: 'tc.list'

# With this permission, a user can toggle the TeamChat:
permission_toggle: 'tc.toggle'

# With this permission, a user will be automatically logged in when he enters the Network:
permission_autologin: 'tc.autologin'

# With this permission, a user is able to enter the GhostMode:
permission_ghostmode: 'tc.ghost'

# With this permission, a user will enter the GhostMode when he joins.
permission_autoghostmode: 'tc.autoghostmode'

# With this permission, a user can reload the Configuration of the Plugin:
permission_reload: 'tc.reload'

# With this permission, a user will get a message when a new Update for the Plugin is available:
permission_update: 'tc.update'
```

## Need help?
If you have any questions, feedback or need help setting it up, please feel free to contact me through <a href="https://github.com/Flurex/TeamChat/issues">Github Issues</a>.
