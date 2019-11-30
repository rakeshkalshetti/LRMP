package com.lrm.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginUser (

    @SerializedName("status") val status : Boolean,
    @SerializedName("statusCode") val statusCode : Int,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<Data>
)

data class Data (

    @SerializedName("token_type") val token_type : String,
    @SerializedName("refresh_token") val refresh_token : String,
    @SerializedName("expires_in") val expires_in : String,
    @SerializedName("scope") val scope : String,
    @SerializedName("isAllowed") val isAllowed : Boolean,
    @SerializedName("isFirstLoginStatus") val isFirstLoginStatus : Boolean,
    @SerializedName("userName") val userName : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("mobileNumber") val mobileNumber : String,
    @SerializedName("isOtpVerifiedStatus") val isOtpVerifiedStatus : Boolean,
    @SerializedName("userType") val userType : String,
    @SerializedName("profilePicUrl") val profilePicUrl : String,
    @SerializedName("roles") val roles : List<String>,
    @SerializedName("appLanguagePref") val appLanguagePref : String,
    @SerializedName("access_token") val access_token : String
)


data class DashBoardList (

    @SerializedName("status") val status : Boolean,
    @SerializedName("statusCode") val statusCode : Int,
    @SerializedName("message") val message : String,
    @SerializedName("totalCount") val totalCount : String,
    @SerializedName("data") val data : MutableList<DashBoard>
)

data class DashBoard (

    @Expose
    @SerializedName("name") val name : String,
    @Expose
    @SerializedName("mobileNumber") val mobileNumber : String,
    @Expose
    @SerializedName("lifelineDocuments") val lifelineDocuments : List<String>,

    /*@Expose
    @SerializedName("createdDate") val createdDate : String,
    @Expose
    @SerializedName("profession") val profession : String,
    @Expose
    @SerializedName("educationalQualification") val educationalQualification : String,
    @Expose
    @SerializedName("surplus_deficit") val surplus_deficit : String,
    @Expose
    @SerializedName("familyMembers") val familyMembers : String,
    @Expose
    @SerializedName("income") val income : String,
    @Expose
    @SerializedName("expense") val expense : String,
    @Expose
    @SerializedName("educationalExpense") val educationalExpense : String,
    @Expose
    @SerializedName("emiExpense") val emiExpense : String,
    @Expose
    @SerializedName("iWish") val iWish : String,
    @Expose
    @SerializedName("iWouldRather") val iWouldRather : String,
    @Expose
    @SerializedName("familyFinancialInsecurity") val familyFinancialInsecurity : String,
    @Expose
    @SerializedName("familyLifeStyleInsecurity") val familyLifeStyleInsecurity : String,
    @Expose
    @SerializedName("familyFutureLiability") val familyFutureLiability : String,
    @Expose
    @SerializedName("jobInsecurity") val jobInsecurity : String,
    @Expose
    @SerializedName("riskOnEventualityToFamily") val riskOnEventualityToFamily : String,
    @Expose
    @SerializedName("overallInsecurity") val overallInsecurity : Double,
    @Expose
    @SerializedName("adviceGiven") val adviceGiven : String,
    @Expose
    @SerializedName("leadFor") val leadFor : String,
    @Expose
    @SerializedName("alternativeNumber") val alternativeNumber : String,

    @Expose
    @SerializedName("alternateMobileNumber") val alternateMobileNumber : String,
    @Expose
    @SerializedName("address") val address : String,
    @Expose
    @SerializedName("dob") val dob : String,
    @Expose
    @SerializedName("age") val age : String,
    @Expose
    @SerializedName("gender") val gender : String,
    @Expose
    @SerializedName("meraDhanCompleted") val meraDhanCompleted : Boolean,
    @Expose
    @SerializedName("meriDuniyaCompleted") val meriDuniyaCompleted : Boolean,
    @Expose
    @SerializedName("mereSapneCompleted") val mereSapneCompleted : Boolean,
    @Expose
    @SerializedName("suggestionCompleted") val suggestionCompleted : Boolean,
    @Expose
    @SerializedName("convertedToLead") val convertedToLead : Boolean,
    @Expose
    @SerializedName("isLACompleted") val isLACompleted : Boolean,
    @Expose
    @SerializedName("isSelfCompleted") val isSelfCompleted : Boolean,
    @Expose
    @SerializedName("isLifelineDocumentUploaded") val isLifelineDocumentUploaded : Boolean,
    @Expose
    @SerializedName("status") val status : String,
    @Expose
    */@SerializedName("notes") val notes : String
)