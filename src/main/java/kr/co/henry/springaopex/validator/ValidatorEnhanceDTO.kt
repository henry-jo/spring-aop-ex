package kr.co.henry.springaopex.validator

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.groups.Default

interface ApiValidator {

    fun validationGroups(): Array<Class<*>?> = emptyArray()
}

data class ValidEhStep1(
    @field:NotBlank
    val name: String?,

    @field:NotBlank
    val address: String?,

    @field:Range(min = 1, max = 150)
    val age: Int?
) : ApiValidator

data class ValidEhStep2(

    @field:NotNull
    val man: Boolean?,

    @field:NotNull(groups = [Man::class])
    val army: Boolean?,

    @field:NotBlank
    val occName: String?,

    @field:NotBlank
    val occAddress: String?
) : ApiValidator {
    interface Man : Default

    override fun validationGroups(): Array<Class<*>?> {
        return man?.let {
            if (it) {
                listOf<Class<*>?>(Man::class.java).toTypedArray()
            } else {
                emptyArray()
            }
        } ?: emptyArray()
    }
}

data class ValidRequest(
    val step1: ValidEhStep1,
    val step2: ValidEhStep2,

    @field:NotNull
    val agreed: Boolean?
) : ApiValidator