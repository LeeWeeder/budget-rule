package com.ljmaq.budgetrule.features.budget_rule.domain.usecase

import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.DeletePartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.GetAllPartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.GetPartitionById
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.InsertPartition
import com.ljmaq.budgetrule.features.budget_rule.domain.usecase.partition.UpdatePartition

data class PartitionUseCases(
    val getAllPartition: GetAllPartition,
    val getPartitionById: GetPartitionById,
    val insertPartition: InsertPartition,
    val deletePartition: DeletePartition,
    val updatePartition: UpdatePartition
)