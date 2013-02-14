/* sbt -- Simple Build Tool
 * Copyright 2008, 2009, 2010 Mark Harrah
 */
package sbt
package classpath

import java.io.File
import sbt.{SimpleFileFilter, DirectoryFilter, IO}
import collection.{JavaConversions, mutable, Set}
import collection.mutable.ListBuffer
import ClasspathUtilities.isArchive

object PathUtilities {
  // Partitions the given classpath into (jars, directories)
  private[sbt] def separate(paths: Iterable[File]): (Iterable[File], Iterable[File]) = paths.partition(isArchive)
  // Partitions the given classpath into (jars, directories)
  private[sbt] def buildSearchPaths(classpath: Iterable[File]): (collection.Set[File], collection.Set[File]) =
  {
    val (jars, dirs) = separate(classpath)
    (linkedSet(jars ++ extraJars), linkedSet(dirs ++ extraDirs))
  }

  	private lazy val (extraJars, extraDirs) =
  	{
  		import scala.tools.nsc.GenericRunnerCommand
  		val settings = (new GenericRunnerCommand(Nil, message => error(message))).settings
  		val bootPaths = IO.pathSplit(settings.bootclasspath.value).map(p => new File(p)).toList
  		val (bootJars, bootDirs) = separate(bootPaths)
  		val extJars =
  		{
  			val buffer = new ListBuffer[File]
  			def findJars(dir: File)
  			{
  				buffer ++= dir.listFiles(new SimpleFileFilter(isArchive))
  				for(dir <- dir.listFiles(DirectoryFilter))
  					findJars(dir)
  			}
  			for(path <- IO.pathSplit(settings.extdirs.value); val dir = new File(path) if dir.isDirectory)
  				findJars(dir)
  			buffer.readOnly.map(_.getCanonicalFile)
  		}
  		(linkedSet(extJars ++ bootJars), linkedSet(bootDirs))
  	}

  private def linkedSet[T](s: Iterable[T]): Set[T] =
  {
    val set: mutable.Set[T] = JavaConversions.asScalaSet(new java.util.LinkedHashSet[T])
    set ++= s
    set
  }
}
