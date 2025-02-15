(ns me.explore.eff
  (:require
   ["effect" :as effect :refer [Effect]]))

(comment
  ;; -----------------------------------------------------------------------------
  ;; BASIC
  ;; -----------------------------------------------------------------------------

  (Effect.succeed 10)

  (Effect.fail "action failed")

  (-> (Effect.succeed 10)
      (Effect.map (fn [x] (+ 1 x)))
      (Effect.runSync))

  (-> (Effect.promise (fn [] (js/Promise. (fn [s _f] (s 10)))))
      (Effect.runPromise)
      (.then prn))

  (-> (Effect.promise (fn [] (js/Promise.resolve 10)))
      (Effect.runPromise)
      (.then prn))

  (-> (Effect.succeed 10)
      (Effect.flatMap (fn [x]
                        (Effect.succeed (+ x 1))))
      (Effect.runSync))

  (-> (Effect.succeed 10)
      (Effect.flatMap (fn [x]
                        ;; WRONG: this effect is not called.
                        ;; See below how to correctly our intent here
                        ;; to log the value of x before further chained
                        (Effect.log "x" x)
                        (Effect.succeed (+ x 1))))
      (Effect.runSync))

  (-> (Effect.succeed 10)
      (Effect.flatMap (fn [x]
                        ;; CORRECT: all effect must be chained
                        (-> (Effect.log "x" x)
                            (Effect.as (+ x 1)))))
      (Effect.runSync))

  ,)


(comment
  ;; -----------------------------------------------------------------------------
  ;; Effect module inspections
  ;; -----------------------------------------------------------------------------

  (->> (js/Object.entries effect)
       (mapv first))

  ["Arbitrary" "Array"
   "BigDecimal" "BigInt" "Boolean" "Brand"
   "Cache" "Cause" "Channel" "ChildExecutorDecision" "Chunk" "Clock" "Config" "ConfigError"
   "ConfigProvider" "ConfigProviderPathPatch" "Console" "Context" "Cron"
   "Data" "DateTime" "DefaultServices" "Deferred" "Differ" "Duration"
   "Effect" "Effectable" "Either" "Encoding" "Equal" "Equivalence" "ExecutionStrategy" "Exit"
   "FastCheck" "Fiber" "FiberHandle" "FiberId" "FiberMap" "FiberRef" "FiberRefs" "FiberRefsPatch"
   "FiberSet" "FiberStatus" "Function"
   "GlobalValue" "GroupBy"
   "HKT" "Hash" "HashMap" "HashSet"
   "Inspectable" "Iterable"
   "JSONSchema"
   "KeyedPool"
   "Layer" "List" "LogLevel" "LogSpan" "Logger"
   "Mailbox" "ManagedRuntime" "Match" "MergeDecision" "MergeState" "MergeStrategy"
   "Metric" "MetricBoundaries" "MetricHook" "MetricKey" "MetricKeyType" "MetricLabel" "MetricPair"
   "MetricPolling" "MetricRegistry" "MetricState" "Micro" "ModuleVersion" "MutableHashMap" "MutableHashSet"
   "MutableList" "MutableQueue" "MutableRef"
   "NonEmptyIterable" "Number"
   "Option" "Order" "Ordering"
   "ParseResult" "Pipeable" "Pool" "Predicate" "Pretty" "PrimaryKey" "PubSub"
   "Queue"
   "Random" "RateLimiter" "RcMap" "RcRef" "Readable" "Record" "RedBlackTree" "Redacted"
   "Ref" "RegExp" "Reloadable" "Request" "RequestBlock" "RequestResolver" "Resource" "Runtime"
   "RuntimeFlags" "RuntimeFlagsPatch"
   "STM" "Schedule" "ScheduleDecision" "ScheduleInterval" "ScheduleIntervals"
   "Scheduler" "Schema" "SchemaAST" "Scope" "ScopedCache" "ScopedRef" "Secret" "SingleProducerAsyncInput"
   "Sink" "SortedMap" "SortedSet" "Stream" "StreamEmit" "StreamHaltStrategy" "Streamable" "String"
   "Struct" "Subscribable" "SubscriptionRef" "Supervisor" "Symbol" "SynchronizedRef"
   "TArray" "TDeferred"
   "TMap" "TPriorityQueue" "TPubSub" "TQueue" "TRandom" "TReentrantLock" "TRef" "TSemaphore" "TSet"
   "TSubscriptionRef" "Take" "TestAnnotation" "TestAnnotationMap" "TestAnnotations" "TestClock" "TestConfig"
   "TestContext" "TestLive" "TestServices" "TestSized" "Tracer" "Trie" "Tuple" "Types"
   "Unify" "UpstreamPullRequest"
   "UpstreamPullStrategy" "Utils"
   "absurd" "flow" "hole" "identity" "pipe" "unsafeCoerce"]


  (->> (js/Object.entries (.-Effect effect))
       (mapv first))

  ["Do" "EffectTypeId" "Service" "Tag" "acquireRelease" "acquireReleaseInterruptible" "acquireUseRelease"
   "addFinalizer" "all" "allSuccesses" "allWith" "allowInterrupt" "andThen" "annotateCurrentSpan"
   "annotateLogs" "annotateLogsScoped" "annotateSpans" "ap" "as" "asSome" "asSomeError" "asVoid"
   "async" "asyncEffect" "awaitAllChildren" "bind" "bindAll" "bindTo" "blocked" "cacheRequestResult"
   "cached" "cachedFunction" "cachedInvalidateWithTTL" "cachedWithTTL" "catch" "catchAll" "catchAllCause"
   "catchAllDefect" "catchIf" "catchSome" "catchSomeCause" "catchSomeDefect" "catchTag" "catchTags"
   "cause" "checkInterruptible" "clock" "clockWith" "configProviderWith" "console" "consoleWith"
   "context" "contextWith" "contextWithEffect" "currentParentSpan" "currentSpan" "custom" "daemonChildren"
   "delay" "descriptor" "descriptorWith" "die" "dieMessage" "dieSync" "diffFiberRefs" "disconnect"
   "dropUntil" "dropWhile" "either" "ensuring" "ensuringChild" "ensuringChildren" "eventually" "every"
   "exists" "exit" "fail" "failCause" "failCauseSync" "failSync" "fiberId" "fiberIdWith" "filter"
   "filterEffectOrElse" "filterEffectOrFail" "filterMap" "filterOrDie" "filterOrDieMessage" "filterOrElse"
   "filterOrFail" "finalizersMask" "findFirst" "firstSuccessOf" "flatMap" "flatten" "flip" "flipWith"
   "fn" "fnUntraced" "forEach" "forever" "fork" "forkAll" "forkDaemon" "forkIn" "forkScoped" "forkWithErrorHandler"
   "fromFiber" "fromFiberEffect" "fromNullable" "functionWithSpan" "gen" "getFiberRefs" "getRuntimeFlags"
   "head" "if" "ignore" "ignoreLogged" "inheritFiberRefs" "interrupt" "interruptWith" "interruptible"
   "interruptibleMask" "intoDeferred" "isEffect" "isFailure" "isSuccess" "iterate" "labelMetrics"
   "labelMetricsScoped" "let" "liftPredicate" "linkSpans" "locally" "locallyScoped" "locallyScopedWith"
   "locallyWith" "log" "logAnnotations" "logDebug" "logError" "logFatal" "logInfo" "logTrace" "logWarning"
   "logWithLevel" "loop" "makeLatch" "makeSemaphore" "makeSpan" "makeSpanScoped" "map" "mapAccum" "mapBoth"
   "mapError" "mapErrorCause" "mapInputContext" "match" "matchCause" "matchCauseEffect" "matchEffect"
   "merge" "mergeAll" "metricLabels" "negate" "never" "none" "onError" "onExit" "onInterrupt" "once"
   "option" "optionFromOptional" "orDie" "orDieWith" "orElse" "orElseFail" "orElseSucceed" "parallelErrors"
   "parallelFinalizers" "partition" "patchFiberRefs" "patchRuntimeFlags" "promise" "provide" "provideService"
   "provideServiceEffect" "race" "raceAll" "raceFirst" "raceWith" "random" "randomWith" "reduce"
   "reduceEffect" "reduceRight" "reduceWhile" "repeat" "repeatN" "repeatOrElse" "replicate" "replicateEffect"
   "request" "retry" "retryOrElse" "runCallback" "runFork" "runPromise" "runPromiseExit" "runRequestBlock"
   "runSync" "runSyncExit" "runtime" "sandbox" "schedule" "scheduleForked" "scheduleFrom" "scope"
   "scopeWith" "scoped" "scopedWith" "sequentialFinalizers" "serviceConstants" "serviceFunction"
   "serviceFunctionEffect" "serviceFunctions" "serviceMembers" "serviceOption" "serviceOptional"
   "setFiberRefs" "sleep" "spanAnnotations" "spanLinks" "step" "succeed" "succeedNone"
   "succeedSome" "summarized" "supervised" "suspend" "sync" "tagMetrics" "tagMetricsScoped" "takeUntil"
   "takeWhile" "tap" "tapBoth" "tapDefect" "tapError" "tapErrorCause" "tapErrorTag" "timed"
   "timedWith" "timeout" "timeoutFail" "timeoutFailCause" "timeoutOption" "timeoutTo" "tracer"
   "tracerWith" "transplant" "transposeOption" "try" "tryMap" "tryMapPromise" "tryPromise" "uninterruptible"
   "uninterruptibleMask" "unless" "unlessEffect" "unsafeMakeLatch" "unsafeMakeSemaphore" "unsandbox"
   "updateFiberRefs" "updateService" "useSpan" "using" "validate" "validateAll" "validateFirst"
   "validateWith" "void" "when" "whenEffect" "whenFiberRef" "whenLogLevel" "whenRef" "whileLoop"
   "withClock" "withClockScoped" "withConcurrency" "withConfigProvider" "withConfigProviderScoped"
   "withConsole" "withConsoleScoped" "withEarlyRelease" "withFiberRuntime" "withLogSpan" "withMaxOpsBeforeYield"
   "withMetric" "withParentSpan" "withRandom" "withRandomScoped" "withRequestBatching"
   "withRequestCache" "withRequestCaching" "withRuntimeFlagsPatch" "withRuntimeFlagsPatchScoped" "withScheduler"
   "withSchedulingPriority" "withSpan" "withSpanScoped" "withTracer" "withTracerEnabled" "withTracerScoped"
   "withTracerTiming" "withUnhandledErrorLogLevel" "yieldNow" "zip"
   "zipLeft" "zipRight" "zipWith"]


  (def Either (.-Either effect))
  (Either.right 10)
  (Either.left "invalid")

  ,)
