load(
    "@io_bazel_rules_scala//scala:scala.bzl",
    "scala_library",
    "scala_test_suite",
)

java_library(
    name = "scalaudio-rpc-java",
    deps = [
        "@maven//:org_apache_thrift_libthrift",
        "@maven//:org_eclipse_jetty_jetty_servlet",
        "@maven//:org_eclipse_jetty_jetty_servlets",
        "@maven//:org_slf4j_slf4j_api",
    ],
    srcs = [
        "src/main/java/scalaudio/rpc/thrift/generated/ScalaudioService.java"
    ],
    exports = [
        "@maven//:org_apache_thrift_libthrift",
    ],
)

scala_library(
    name = "scalaudio-rpc",
    srcs = glob(["src/main/**/*.scala"]),
    deps = [
        ":scalaudio-rpc-java",
        "//scalaudio-core:scalaudio-core",
        "@maven//:org_eclipse_jetty_jetty_servlet",
        "@maven//:org_eclipse_jetty_jetty_servlets"
    ],
    visibility = ["//visibility:public"]
)

scala_library(
    name = "scalaudio-rpc-test-utils",
    srcs = glob(["src/it/**/*.scala"]),
    deps = [
        ":scalaudio-rpc",
        "@maven//:junit_junit",
        "@maven//:org_scalatest_scalatest_2_11"
    ],
    visibility = ["//visibility:public"]

)

scala_test_suite(
    name = "scalaudio-rpc-tests",
    srcs = glob(["src/test/**/*.scala"]),
    deps = [
        ":scalaudio-rpc",
        ":scalaudio-rpc-test-utils",
        "@maven//:junit_junit",
        "@maven//:org_scalatest_scalatest_2_11"
    ]
)
