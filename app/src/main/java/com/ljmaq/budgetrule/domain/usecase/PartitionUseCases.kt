package com.ljmaq.budgetrule.domain.usecase

import com.ljmaq.budgetrule.domain.usecase.partition.DeletePartition
import com.ljmaq.budgetrule.domain.usecase.partition.GetAllPartition
import com.ljmaq.budgetrule.domain.usecase.partition.GetPartitionById
import com.ljmaq.budgetrule.domain.usecase.partition.InsertPartition
import com.ljmaq.budgetrule.domain.usecase.partition.UpdatePartition

data class PartitionUseCases(
    val getAllPartition: GetAllPartition,
    val getPartitionById: GetPartitionById,
    val insertPartition: InsertPartition,
    val deletePartition: DeletePartition,
    val updatePartition: UpdatePartition
)