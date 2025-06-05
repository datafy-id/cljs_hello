# Hello CLJS

Yes, its 2025. Long live clojure, long live repl.

This project was created using `bun init` in bun v1.2.0. [Bun](https://bun.sh) is a fast all-in-one JavaScript runtime.

## TLDR;

1. Open the project with emacs.

2. `M-x cider-jack-in-cljs` then choose `shadow` then choose `:app` or `:cli` build for target browser and target node-script respectively.

3. You're on roll now.

Open browser http://localhost:8000 (for build target browser) or execute `bun run out/me/main.js` (for build target node) to connect nrepl client (emacs) to the runtime (browser or bun).

From cider cljs repl, we can switch to its sibling clojure repl with: `M-x cider-connect-sibling-clj`.

From within clojure repl, we can compile, watch etc like this:

```clojure
;; Compile
(shadow.cljs.devtools.api/compile :cli)

;; Watch
(shadow.cljs.devtools.api/watch :cli)

;; Unwatch
(shadow.cljs.devtools.api/stop-worker :cli)

;; Switch / enter the cljs repl
(shadow.cljs.devtools.api/node-repl {:build-id :cli})

;; Quit from cljs repl
:cljs/quit
```

## REFERENCES

- https://shadow-cljs.github.io/docs/UsersGuide.html
- https://github.com/amqp-node/amqplib
- https://node-postgres.com/
- https://effect.website/
- https://date-fns.org/
