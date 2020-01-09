load(
    "@io_bazel_rules_scala//scala:scala.bzl",
    "scala_library",
    "scala_test_suite",
)

scala_library(
    name = "scalaudio-core",
    srcs = glob(["src/main/**/*.scala"]),
    deps = [
        "@maven//:org_auroboros_signal_z_2_11",
        "@maven//:org_clojars_sidec_jsyn",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:ch_qos_logback_logback_classic",
        "@maven//:org_scalaz_scalaz_core_2_11",
    ],
    exports = [
        "@maven//:org_auroboros_signal_z_2_11",
        "@maven//:org_clojars_sidec_jsyn",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_scalaz_scalaz_core_2_11",

    ],
    visibility = ["//visibility:public"]
)

scala_library(
    name = "scalaudio-core-test-utils",
    srcs = glob(["src/it/**/*.scala"]),
    deps = [
        ":scalaudio-core",
        "@maven//:org_scalatest_scalatest_2_11"
    ],
    exports = [
        ":scalaudio-core",
    ],
    visibility = ["//visibility:public"]

)

scala_test_suite(
    name = "scalaudio-core-tests",
    srcs = glob(["src/test/**/*.scala"]),
    deps = [
        ":scalaudio-core",
        "@maven//:junit_junit",
        "@maven//:org_scalatest_scalatest_2_11"
    ],
    visibility = ["//visibility:public"]
)
