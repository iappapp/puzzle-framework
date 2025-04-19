package cn.codependency.framework.puzzle.generator.dsl

@PuzzleGenerator
class IntermediateLinkBuilder {
    internal var leftModel: String? = null
    internal var leftRefField: String? = null
    internal var leftRefLabel: String? = null
    internal var leftRefName: String? = null

    internal var rightModel: String? = null
    internal var rightRefField: String? = null
    internal var rightRefLabel: String? = null
    internal var rightRefName: String? = null

    fun left(refName: String, refLabel: String, model: String, refField: String) {
        leftRefName = refName
        leftRefLabel = refLabel
        leftModel = model
        leftRefField = refField
    }

    fun right(refName: String, refLabel: String, model: String, refField: String) {
        rightRefName = refName
        rightRefLabel = refLabel
        rightModel = model
        rightRefField = refField
    }

    fun build(): IntermediateLink {
        require(leftRefName != null && rightRefName != null) {
            "Both left and right references must be set"
        }

        return IntermediateLink(
            leftRefName = leftRefName!!,
            leftRefLabel = leftRefLabel!!,
            leftModel = leftModel!!,
            leftRefField = leftRefField!!,
            rightRefName = rightRefName!!,
            rightRefLabel = rightRefLabel!!,
            rightModel = rightModel!!,
            rightRefField = rightRefField!!
        )
    }
}
