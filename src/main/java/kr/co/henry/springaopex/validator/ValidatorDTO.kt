package kr.co.henry.springaopex.validator

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.groups.Default

data class SignUpStep1(
    @field:NotBlank
    val name: String?,

    @field:NotBlank
    val address: String?,

    @field:Range(min = 1, max = 150)
    val age: Int?
)

data class SignUpStep2(

    @field:NotNull
    val man: Boolean?,

    @field:NotNull(groups = [Man::class])
    val army: Boolean?,

    @field:NotBlank
    val occName: String?,

    @field:NotBlank
    val occAddress: String?
) {
    interface Man : Default
}

data class SignUpRequest(
    val step1: SignUpStep1,
    val step2: SignUpStep2,

    @field:NotNull
    val agreed: Boolean?
)