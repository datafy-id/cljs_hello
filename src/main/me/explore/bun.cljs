(ns me.explore.bun
  (:require
   [shadow.cljs.modern :refer (js-await)]
   ["bun" :as bun]))


(comment
  ,

  (->> (js/Object.entries bun)
       (mapv first))

  ["pathToFileURL" "fileURLToPath" "enableANSIColors" "deepEquals" "env"
   "fetch" "file" "CryptoHasher" "dns" "connect" "$" "ArrayBufferSink" "FFI"
   "FileSystemRouter" "Glob" "MD4" "MD5" "SHA1" "SHA224" "SHA256" "SHA384" "SHA512"
   "SHA512_256" "TOML" "Transpiler" "embeddedFiles" "S3Client" "s3" "allocUnsafe" "argv"
   "build" "concatArrayBuffers" "color" "deepMatch" "deflateSync" "escapeHTML"
   "gc" "generateHeapSnapshot" "gunzipSync" "gzipSync" "hash" "indexOfLine" "inflateSync"
   "inspect" "isMainThread" "listen" "udpSocket" "main" "mmap" "nanoseconds"
   "openInEditor" "origin" "version_with_sha" "password" "peek" "plugin" "randomUUIDv7"
   "readableStreamToArray" "readableStreamToArrayBuffer" "readableStreamToBytes" "readableStreamToBlob"
   "readableStreamToFormData" "readableStreamToJSON" "readableStreamToText" "resolve"
   "resolveSync" "revision" "semver" "sql" "postgres" "SQL" "serve" "sha" "shrink"
   "sleep" "sleepSync" "spawn" "spawnSync" "stderr" "stdin" "stdout" "stringWidth"
   "unsafe" "version" "which" "write"]


  ,)


(comment
  ,

  ;; Guides > Binary data
  ;; -----------------------------------------------------------------------------

  ;; ---
  ;; Convert an ArrayBuffer to an array of numbers with Bun

  ;; To retrieve the contents of an ArrayBuffer as an array of numbers, create a
  ;; Uint8Array over of the buffer. and use the Array.from() method to convert
  ;; it to an array.

  (def buf (js/ArrayBuffer. 64))
  (def arr (js/Uint8Array. buf))
  (js/Array.from arr)

  ;; The Uint8Array class supports array indexing and iteration. However if you
  ;; wish to convert the instance to a regular Array, use Array.from(). (This
  ;; will likely be slower than using the Uint8Array directly.)
  (.-length arr) ;; 64
  (aget arr 0) ;; 0


  ;; ---
  ;; Convert an ArrayBuffer to a Blob with Bun

  ;; A Blob can be constructed from an array of "chunks", where each chunk is a
  ;; string, binary data structure, or another Blob.
  (def blob (js/Blob. #js [buf]))

  ;; By default the type of the resulting Blob will be unset. This can be set manually.
  (.-type blob) ;; => ""

  (def blob2 (js/Blob. #js [buf] #js {:type "application/octet-stream"}))
  (.-type blob2) ;; => "application/octet-stream"


  ;; ---
  ;; Convert an ArrayBuffer to a Buffer with Bun

  ;; The Node.js Buffer API predates the introduction of ArrayBuffer into the
  ;; JavaScript language. Bun implements both.
  (def arrBuffer (js/ArrayBuffer. 64))

  ;; Use the static Buffer.from() method to create a Buffer from an ArrayBuffer.
  (def nodeBuffer (js/Buffer.from arrBuffer))
  (aget nodeBuffer 0)

  ;; To create a Buffer that only views a portion of the underlying buffer, pass
  ;; the offset and length to the constructor.
  (def nodeBuffer2 (js/Buffer.from arrBuffer 0 16)) ;; view the first 16 bytes
  (alength nodeBuffer2) ;; => 16


  ;; ---
  ;; Convert an ArrayBuffer to a string with Bun

  ;; Bun implements the Web-standard TextDecoder class for converting between
  ;; binary data types and strings.
  (def buf (js/ArrayBuffer. 64))
  (def decoder (js/TextDecoder.))
  (def str1 (.. decoder (decode buf)))
  (alength str1) ;; => 64


  ;; ---
  ;; Convert an ArrayBuffer to a Uint8Array with Bun

  ;; A Uint8Array is a typed array, meaning it is a mechanism for viewing the
  ;; data in an underlying ArrayBuffer.
  (def buffer (js/ArrayBuffer. 64))
  (def arr (js/Uint8Array. buffer))

  ;; Instances of other typed arrays can be created similarly.
  (def arr1 (js/Uint8Array. buffer))
  (def arr2 (js/Uint16Array. buffer))
  (def arr3 (js/Uint32Array. buffer))
  (def arr4 (js/Float32Array. buffer))
  (def arr5 (js/Float64Array. buffer))
  (def arr6 (js/BigInt64Array. buffer))
  (def arr7 (js/BigUint64Array. buffer))
  (->> [arr1 arr2 arr3 arr4 arr5 arr6 arr7]
       (map (fn [x] (.-length x)))) ;; => '(64 32 16 16 8 8 8)


  ;; ---
  ;; Convert a Blob to an ArrayBuffer, DataView, ReadableStream, String

  ;; The Blob class provides a number of methods for consuming its contents in
  ;; different formats, including .arrayBuffer().
  (def blob (js/Blob. #js ["hello world"]))
  (js-await [x (.. blob (arrayBuffer))]
    (def buf x))
  (type buf) ;; #object [ArrayBuffer]
  (js/Array.from (js/Uint8Array. buf))

  ;; .arrayBuffer() -> DataView
  (js-await [x (.. blob (arrayBuffer))]
    (def dataview (js/DataView. x)))
  (type dataview)

  ;; .stream()
  (def stream (.. blob (stream)))
  (type stream) ;; => #object [ReadableStream]

  ;; .text()
  (js-await [x (.. blob (text))]
    (def str2 x))
  str2 ;; => "hello world"



  ;; ---
  ;; Convert a Buffer to an ArrayBuffer, Blob, etc

  ;; The Node.js Buffer class provides a way to view and manipulate data in an
  ;; underlying ArrayBuffer, which is available via the buffer property.
  (def nodeBuf (js/Buffer.alloc 64))
  (def arrBuf (.. nodeBuf -buffer))
  (type arrBuf) ;; => #object [ArrayBuffer]

  ;; A Blob can be constructed from an array of "chunks", where each chunk is a
  ;; string, binary data structure (including Buffer), or another Blob.
  (def nodeBuf (js/Buffer.from "hello"))
  (def blob (js/Blob. #js[nodeBuf]))

  ;; The naive approach to creating a ReadableStream from a Buffer is to use the
  ;; ReadableStream constructor and enqueue the entire array as a single chunk.
  ;; For a large buffer, this may be undesirable as this approach does
  ;; not "streaming" the data in smaller chunks.
  (def nodeBuf (js/Buffer.from "hello world"))
  (def stream (js/ReadableStream.
               #js {:start (fn [controller]
                             (.. controller (enqueue nodeBuf))
                             (.. controller (close)))}))

  ;; To stream the data in smaller chunks, first create a Blob instance from the
  ;; Buffer. Then use the Blob.stream() method to create a ReadableStream that
  ;; streams the data in chunks of a specified size.
  (def nodeBuf (js/Buffer.from "hello world"))
  (def blob (js/Blob. #js [nodeBuf]))
  (def stream (.. blob (stream)))
  (type stream) ;; => #object [ReadableStream]

  ;; The chunk size can be set by passing a number to the .stream() method.
  (def stream2 (.. blob (stream 1024)))
  (js/console.log stream2)

  ;; .toString()
  (def nodeBuf (js/Buffer.from "hello world"))
  (.. nodeBuf (toString)) ;; => "hello world"

  ;; The Node.js Buffer class extends Uint8Array, so no conversion is needed.
  ;; All properties and methods on Uint8Array are available on Buffer.
  (instance? js/Uint8Array nodeBuf) ;; => true



  ;; Convert a DataView to a string with Bun

  ;; If a DataView contains ASCII-encoded text, you can convert it to a string
  ;; using the TextDecoder class.
  (def blob (js/Blob. #js ["hello world"]))
  (js-await [x (.. blob (arrayBuffer))]
    (def dataview (js/DataView. x)))
  (def decoder (js/TextDecoder.))
  (.. decoder (decode dataview)) ;; => "hello world"


  ,)



(comment
  ,

  ;; Guides > Raading Files

  ;; Read a JSON file with Bun

  ;; The Bun.file() function accepts a path and returns a BunFile instance. The
  ;; BunFile class extends Blob and allows you to lazily read the file in a
  ;; variety of formats. Use .json() to read and parse the contents of a .json
  ;; file as a plain object.
  (def file (js/Bun.file "./package.json"))
  (js-await [x (.. file (json))]
    (def contents x))
  contents

  ;; The MIME type of the BunFile will be set accordingly.
  (.-type file) ;; => "application/json;charset=utf-8"

  ;; Use the .exists() to check if a file exists at given path.
  (-> (.. file (exists))
      (.then js/console.log))

  ;; To read the file into a Buffer instance, first use .arrayBuffer() to
  ;; consume the file as an ArrayBuffer, then use Buffer.from() to create a
  ;; Buffer from the ArrayBuffer.
  (js-await [x (.. file (arrayBuffer))]
    (def arrBuf x))
  (def nodeBuffer (js/Buffer.from arrBuf))

  ;; Use .text() to read the contents as a string.
  (js-await [x (.. file (text))]
    (def text x))
  text

  ;; Use .stream() to consume the file incrementally as a ReadableStream.
  (def stream (.. file (stream)))


  ,)
