package cn.codependency.framework.puzzle.generator.dsl

// 假设的 IntermediateLink 数据类
data class IntermediateLink(
    val leftRefName: String,
    val leftRefLabel: String,
    val leftModel: String,
    val leftRefField: String,
    val rightRefName: String,
    val rightRefLabel: String,
    val rightModel: String,
    val rightRefField: String
)
