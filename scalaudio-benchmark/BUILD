load(
    "@io_bazel_rules_scala//scala:scala.bzl",
    "scala_library",
    "scala_test",
)

scala_library(
    name = "scalaudio-benchmark",
    srcs = glob(["src/main/**/*.scala"]),
    deps = [
        "//scalaudio-core:scalaudio-core",
        "//scalaudio-rpc:scalaudio-rpc",
    ]
)

scala_test(
    name = "scalaudio-benchmark-tests",
    srcs = glob(["src/test/**/*.scala"]),
    deps = [
        ":scalaudio-benchmark",
        "@maven//:junit_junit",
        "@maven//:scalatest_scalatest"
    ]
)
