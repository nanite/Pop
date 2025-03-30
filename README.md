# Pop

Pop is a small helper mod for modpack developers to allow for more screen locations to display information on screen in a command driven, or kubejs driven way.

## Features

- Display information on screen in a command driven way (`/pop create @s MIDDLE_LEFT "Hello World!"`)
- Supports translations out of the box
- [ ] (Coming soon) progress bars
- [ ] (Coming soon) headings & subheadings
- [ ] (Coming soon) customisation via commands as well as code
- [ ] (Coming soon) more animations for text

## How to use

1. Install the mod in your modpack
2. Run the game
3. Use the `/pop` command to create a new pop up

Alternatively, you can use KubeJS to access the Pop Manager and the Pop Builder.

### Example

```javascript
// server.js
const PopBuilder = java.load('pro.mikey.mods.pop.PopBuilder');
const Placement = java.load('pro.mikey.mods.pop.data.Placement'); // Optional
const pop = PopBuilder.create()
    .duration(5)
    .content({ text: "Hello World!" })
    .placement("middle_left") // Or  .placement(Placement.MIDDLE_LEFT)

// You can now either build it and get a PopData object or directly send it to the player
// If you're on the server
pop.sendToPlayer(player);

// If you're on the client
pop.display()
```

## Requests

If you have any requests for features, please open an issue on the GitHub repository. I will try to get to them as soon as possible. I'm always looking for new ideas to improve the mod and make it more useful for modpack developers.

