load(
    "@io_bazel_rules_scala//scala:scala.bzl",
    "scala_library",
    "scala_test_suite",
)

scala_library(
    name = "scalaudio-amp",
    srcs = glob(["src/main/**/*.scala"]),
    deps = [
        "//scalaudio-core:scalaudio-core",
    ],
    exports = [
        "//scalaudio-core:scalaudio-core",
    ],
    visibility = ["//visibility:public"]
)

scala_library(
    name = "scalaudio-amp-test-utils",
    srcs = glob(["src/it/**/*.scala"]),
    deps = [
        ":scalaudio-amp",
        "//scalaudio-core:scalaudio-core-test-utils",
        "@maven//:junit_junit",
        "@maven//:org_scalatest_scalatest_2_11",
    ],
    exports = [
        "//scalaudio-core:scalaudio-core-test-utils",

    ],
    visibility = ["//visibility:public"]
)

scala_test_suite(
    name = "scalaudio-amp-tests",
    srcs = glob(["src/test/**/*.scala"]),
    deps = [
        ":scalaudio-amp",
        ":scalaudio-amp-test-utils",
        "@maven//:junit_junit",
        "@maven//:org_scalatest_scalatest_2_11"
    ],
    visibility = ["//visibility:public"]
)
