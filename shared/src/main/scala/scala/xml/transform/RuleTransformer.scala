/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala
package xml
package transform

import scala.collection.immutable.{Seq => ISeq}

class RuleTransformer(rules: RewriteRule*) extends BasicTransformer {
  private val transformers: ISeq[NestingTransformer] = rules.map(new NestingTransformer(_))

  override def transform(n: Node): ISeq[Node] =
    if (transformers.isEmpty) n
    else transformers.tail.foldLeft(transformers.head.transform(n)) { (res, transformer) => transformer.transform(res) }
}
