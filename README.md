Stuff I'd improve given infinite time:
- cover screen itself with tests
  - that it scrolls to top after a row is clicked
  - that keyboard is shown at the right time
- find a more elegant way of scrolling after items have updated than waiting 100ms
- test that holder doesn't overwrite user's input while it's focused
- add a common debug keystore to avoid certificate conflicts
- refactor for loop manipulations into smaller better named functions
- use DI to construct test variants of stuff, including injecting schedulers
