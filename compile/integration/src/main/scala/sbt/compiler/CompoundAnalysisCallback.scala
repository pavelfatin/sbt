package sbt
package compiler

import java.io.File
import xsbti.{Severity, Position, AnalysisCallback}
import xsbti.api.SourceAPI

class CompoundAnalysisCallback(delegates: AnalysisCallback*) extends AnalysisCallback {
  def beginSource(source: File) {
    delegates.foreach(_.beginSource(source))
  }

  def sourceDependency(dependsOn: File, source: File) {
    delegates.foreach(_.sourceDependency(dependsOn, source))
  }

  def binaryDependency(binary: File, name: String, source: File) {
    delegates.foreach(_.binaryDependency(binary, name, source))
  }

  def generatedClass(source: File, module: File, name: String) {
    delegates.foreach(_.generatedClass(source, module, name))
  }

  def endSource(sourcePath: File) {
    delegates.foreach(_.endSource(sourcePath))
  }

  def api(sourceFile: File, source: SourceAPI) {
    delegates.foreach(_.api(sourceFile, source))
  }

  def problem(what: String, pos: Position, msg: String, severity: Severity, reported: Boolean) {
    delegates.foreach(_.problem(what, pos, msg, severity, reported))
  }
}
