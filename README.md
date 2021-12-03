First and foremost, I would like to apologize for the fact that my demonstration is almost 12 minutes long. I cut out a few things from the script I deemed unimportant to the project's
scope, but there are still a lot of things I considered "core mechanics". I've included timestamps in the video description that point to the most important parts of the video.

Link to demo video: https://youtu.be/CdbCzhPDOyo

Also, here is the template mod I used to create my mod: https://github.com/Gremious/StS-DefaultModBase

# What I Learned

Obviously, I learned how to make mods for Slay the Spire, which is something I'll probably do again in the future. I also gained a rudimentary understanding of
Java. Digging into one of my favorite games, especially such a simple one, and learning how it works was probably the best way to learn a new language. Beyond that, in a
more abstract sense, I learned a lot about what game development is like. I got to see deprecated content that I never knew existed, and saw firsthand how difficult
making such a complex game can be. I would have thought this to be quite a simple game code-wise, because it's a turn-based stable environment, but there is so much put
into things I never thought about such as VFX, easy translation, or making non-exploitable RNG. Something I found very interesting was that every single string that gets
printed during gameplay is stored in a .json file rather than hard-coded in the class files that prints it. This is so that translating the game to other languages is easy
and requires very little programming knowledge. Changing the language only requires you to call from a different .json file, rather than going through every file in the game
and changing hard-coded strings.


# Things I Enjoyed

Overall, once I got the hang of it, this project was very enjoyable. Heck, I did 90% of the work during fall break, on my own time. I could have been doing other schoolwork, but I
limited my time off to working on this because it was "the fun project" compared to everything else on my plate. Conceptualizing mods for games like this has been a
very long-time hobby of mine. The idea for this mod is roughly a year old, but has evolved significantly over time, and is now finally real. The nature of this game makes
it very easy to add onto. Since the modding and game are done in the same language (rather than interfacing with something like lua), I was able to learn much of what I
needed just by decompiling the game and looking at its source code, rather than having to seek out tutorials. For the most part, all of my time spent on this project was
spent on bringing my ideas to life. Very little time was wasted wrestling with the API or performing basic setup, due to how good the API was and how good the template was.


# Difficulties I Faced

Many aspects of this project that I thought would be brick walls to my progress were actually quite attainable. In testing (a phase which is certainly not done yet),
I encountered frequent crashes. Most of them, thanks to IntelliJ IDEA's debug features, pointed to a specific line of code and were thus easy to identify and fix. Most
were due to small oversights, such as forgetting to change a value from a card template. Some other problems were harder to fix. The system I showed that adapts card text to
match the player's Strength and Dexterity was probably the most difficult code to write. I had some help from the #modding-technical channel of the Slay the Spire
official Discord server, which was a great resource throughout my project. The people there told me about some examples of cards in the base game that do something similar.
I also ran into a lot of problems while copying that code to other cards that needed it, because each card needed to use it in a slightly different way. Whether the card was
upgraded or not and whether damage was being added or subtracted all required different strings.
