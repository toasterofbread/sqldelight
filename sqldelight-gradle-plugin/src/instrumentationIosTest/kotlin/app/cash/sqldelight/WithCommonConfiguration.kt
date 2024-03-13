package app.cash.sqldelight

import java.io.File
import org.gradle.testkit.runner.GradleRunner

internal fun GradleRunner.withCommonConfiguration(projectRoot: File): GradleRunner {
  File(projectRoot, "gradle.properties").writeText(
    """
      |org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g
    """.trimMargin(),
  )
  return withProjectDir(projectRoot)
}
