/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm.intrinsics

import org.jetbrains.kotlin.backend.jvm.JvmBackendContext
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
import org.jetbrains.kotlin.ir.types.toKotlinType
import org.jetbrains.kotlin.ir.types.typeWith
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature

class IsArrayOf : IntrinsicMethod() {
    override fun toCallable(
        expression: IrMemberAccessExpression,
        signature: JvmMethodSignature,
        context: JvmBackendContext
    ): IrIntrinsicFunction {
        val typeMapper = context.state.typeMapper

        assert(expression.typeArgumentsCount == 1) {
            "Expected only one type parameter for Any?.isArrayOf(), got: ${expression.typeArgumentsCount}"
        }

        val elementType = expression.getTypeArgument(0)!!
        val typeWith = context.irBuiltIns.arrayClass.typeWith(elementType)
        val arrayType = typeMapper.mapType(typeWith.toKotlinType())

        return IrIntrinsicFunction.create(expression, signature, context) {
            it.instanceOf(arrayType)
        }
    }
}