package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogStatus

/**
 * A workout log entry (a planned, in-progress, completed or skipped workout) /
 * Запись в дневнике тренировок (запланированная, в процессе, завершённая или пропущенная тренировка)
 *
 * `adjustmentPercent` is the actual adjustment percent chosen by the user at workout start /
 * `adjustmentPercent` — фактический процент корректировки, выбранный пользователем при старте тренировки
 *
 * FK strategy: userId uses CASCADE (deleting a user deletes their workout logs);
 * programId, microcycleId, dayId use SET_NULL (deleting a program/microcycle/day keeps the log, just unlinks it) /
 * Стратегия FK: userId использует CASCADE (удаление пользователя удаляет его дневник);
 * programId, microcycleId, dayId используют SET_NULL (удаление программы/микроцикла/дня оставляет запись, просто отвязывает её)
 */
@Entity(
    tableName = "workout_logs",
    foreignKeys = [
        ForeignKey(
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = UserEntity::class,
        ),
        ForeignKey(
            childColumns = ["programId"],
            onDelete = ForeignKey.SET_NULL,
            parentColumns = ["id"],
            entity = ProgramEntity::class,
        ),
        ForeignKey(
            childColumns = ["microcycleId"],
            onDelete = ForeignKey.SET_NULL,
            parentColumns = ["id"],
            entity = MicrocycleEntity::class,
        ),
        ForeignKey(
            childColumns = ["dayId"],
            onDelete = ForeignKey.SET_NULL,
            parentColumns = ["id"],
            entity = MicrocycleDayEntity::class,
        ),
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["programId"]),
        Index(value = ["microcycleId"]),
        Index(value = ["dayId"]),
    ],
)
data class WorkoutLogEntity(
    @ColumnInfo(name = "adjustmentPercent") val adjustmentPercent: Double,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "completedAt") val completedAt: Long?,
    @ColumnInfo(name = "dayId") val dayId: Long?,
    @ColumnInfo(name = "microcycleId") val microcycleId: Long?,
    @ColumnInfo(name = "programId") val programId: Long?,
    @ColumnInfo(name = "scheduledDate") val scheduledDate: Long,
    @ColumnInfo(name = "startedAt") val startedAt: Long?,
    @ColumnInfo(name = "status") val status: WorkoutLogStatus,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "userId") val userId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
