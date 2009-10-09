## Programming Scala - Trials and Tribulations

These are the trial and tribulations as I work through the [Programming Scala](http://oreilly.com/catalog/9780596155964/) book by Dean Wampler and Alex Payne.

## Make a little spec, learn a little more

I worked through the book in kind of a different way.

Instead of printing results out (like a `println("Hello world")` script), I tried to approach this as if I was actually developing an app for it. So I learned the language by writing specs and asserting what I thought was supposed to happen. Usually I was wrong, but the tests would steer me in the right direction. I also had to figure out a way of building the application from the get-go. I started with Maven2,as I was very familiar with it, but settled with [simple-build-tool](http://code.google.com/p/simple-build-tool). Simple-build-tool as a "~ _action_" flag which will watch your source directories and run the action on change. `~test-quick` FTW.

## Review

The book has been great. Certainly the most approachable way to learn the language. 4 1/2 stars (minus a point for its explanation of partial functions - not quite right)
