package sbt
package compiler

import java.io.File
import xsbti.{Severity, Position, AnalysisCallback}
import xsbti.api.SourceAPI

class CompileListenerAdapter(listener: CompileListener) extends AnalysisCallback {
  def beginSource(source: File) {}

  def sourceDependency(dependsOn: File, source: File) {}

  def binaryDependency(binary: File, name: String, source: File) {}

  def generatedClass(source: File, module: File, name: String) {
    listener.generated(source, module, name)
  }

  def endSource(sourcePath: File) {}

  def api(sourceFile: File, source: SourceAPI) {}

  def problem(what: String, pos: Position, msg: String, severity: Severity, reported: Boolean) {}
}
