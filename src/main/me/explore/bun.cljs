(ns me.explore.bun
  (:require
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
