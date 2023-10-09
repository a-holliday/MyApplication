package com.example.myapplication
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="questions")
data class Question (@PrimaryKey(autoGenerate = true) val prompt: String?, val answer: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(prompt)
        parcel.writeString(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }


}