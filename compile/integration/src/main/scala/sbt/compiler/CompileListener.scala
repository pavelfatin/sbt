package sbt
package compiler

import java.io.File

trait CompileListener {
  def generated(source: File, module: File, name: String)

  def deleted(module: File)
}
