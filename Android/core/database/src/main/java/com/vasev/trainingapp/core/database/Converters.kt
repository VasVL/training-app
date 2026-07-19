package com.vasev.trainingapp.core.database

import androidx.room.TypeConverter
import com.vasev.trainingapp.core.database.entity.types.ExerciseType
import com.vasev.trainingapp.core.database.entity.types.Gender
import com.vasev.trainingapp.core.database.entity.types.HeightUnit
import com.vasev.trainingapp.core.database.entity.types.MeasurementUnit
import com.vasev.trainingapp.core.database.entity.types.MicrocycleDayType
import com.vasev.trainingapp.core.database.entity.types.MuscleInvolvement
import com.vasev.trainingapp.core.database.entity.types.MuscleRelation
import com.vasev.trainingapp.core.database.entity.types.PrerequisiteType
import com.vasev.trainingapp.core.database.entity.types.ProgramCategory
import com.vasev.trainingapp.core.database.entity.types.RepType
import com.vasev.trainingapp.core.database.entity.types.SetType
import com.vasev.trainingapp.core.database.entity.types.UserRole
import com.vasev.trainingapp.core.database.entity.types.WeightType
import com.vasev.trainingapp.core.database.entity.types.WeightUnit
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogSetStatus
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogStatus

/**
 * Room TypeConverters for all enums used in entities / Room TypeConverters для всех enum-ов в Entity
 *
 * Room stores enums as their `name` (String) in the DB; these converters map String ↔ enum /
 * Room хранит enum-ы как их `name` (String) в БД; эти конвертеры маппят String ↔ enum
 *
 * `internal object` — singleton, stateless, visible only inside the `core/database` module /
 * `internal object` — синглтон, без состояния, виден только внутри модуля `core/database`
 */
internal object Converters {

    @TypeConverter
    fun fromExerciseType(value: ExerciseType?): String? = value?.name

    @TypeConverter
    fun toExerciseType(value: String?): ExerciseType? = value?.let { ExerciseType.valueOf(it) }

    @TypeConverter
    fun fromGender(value: Gender?): String? = value?.name

    @TypeConverter
    fun toGender(value: String?): Gender? = value?.let { Gender.valueOf(it) }

    @TypeConverter
    fun fromHeightUnit(value: HeightUnit?): String? = value?.name

    @TypeConverter
    fun toHeightUnit(value: String?): HeightUnit? = value?.let { HeightUnit.valueOf(it) }

    @TypeConverter
    fun fromMeasurementUnit(value: MeasurementUnit?): String? = value?.name

    @TypeConverter
    fun toMeasurementUnit(value: String?): MeasurementUnit? = value?.let { MeasurementUnit.valueOf(it) }

    @TypeConverter
    fun fromMicrocycleDayType(value: MicrocycleDayType?): String? = value?.name

    @TypeConverter
    fun toMicrocycleDayType(value: String?): MicrocycleDayType? = value?.let { MicrocycleDayType.valueOf(it) }

    @TypeConverter
    fun fromMuscleInvolvement(value: MuscleInvolvement?): String? = value?.name

    @TypeConverter
    fun toMuscleInvolvement(value: String?): MuscleInvolvement? = value?.let { MuscleInvolvement.valueOf(it) }

    @TypeConverter
    fun fromMuscleRelation(value: MuscleRelation?): String? = value?.name

    @TypeConverter
    fun toMuscleRelation(value: String?): MuscleRelation? = value?.let { MuscleRelation.valueOf(it) }

    @TypeConverter
    fun fromPrerequisiteType(value: PrerequisiteType?): String? = value?.name

    @TypeConverter
    fun toPrerequisiteType(value: String?): PrerequisiteType? = value?.let { PrerequisiteType.valueOf(it) }

    @TypeConverter
    fun fromProgramCategory(value: ProgramCategory?): String? = value?.name

    @TypeConverter
    fun toProgramCategory(value: String?): ProgramCategory? = value?.let { ProgramCategory.valueOf(it) }

    @TypeConverter
    fun fromRepType(value: RepType?): String? = value?.name

    @TypeConverter
    fun toRepType(value: String?): RepType? = value?.let { RepType.valueOf(it) }

    @TypeConverter
    fun fromSetType(value: SetType?): String? = value?.name

    @TypeConverter
    fun toSetType(value: String?): SetType? = value?.let { SetType.valueOf(it) }

    @TypeConverter
    fun fromUserRole(value: UserRole?): String? = value?.name

    @TypeConverter
    fun toUserRole(value: String?): UserRole? = value?.let { UserRole.valueOf(it) }

    @TypeConverter
    fun fromWeightType(value: WeightType?): String? = value?.name

    @TypeConverter
    fun toWeightType(value: String?): WeightType? = value?.let { WeightType.valueOf(it) }

    @TypeConverter
    fun fromWeightUnit(value: WeightUnit?): String? = value?.name

    @TypeConverter
    fun toWeightUnit(value: String?): WeightUnit? = value?.let { WeightUnit.valueOf(it) }

    @TypeConverter
    fun fromWorkoutLogSetStatus(value: WorkoutLogSetStatus?): String? = value?.name

    @TypeConverter
    fun toWorkoutLogSetStatus(value: String?): WorkoutLogSetStatus? = value?.let { WorkoutLogSetStatus.valueOf(it) }

    @TypeConverter
    fun fromWorkoutLogStatus(value: WorkoutLogStatus?): String? = value?.name

    @TypeConverter
    fun toWorkoutLogStatus(value: String?): WorkoutLogStatus? = value?.let { WorkoutLogStatus.valueOf(it) }
}
