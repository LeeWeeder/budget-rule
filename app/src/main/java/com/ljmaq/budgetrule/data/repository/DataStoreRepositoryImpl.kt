package com.ljmaq.budgetrule.data.repository

import android.content.Context
import androidx.annotation.FloatRange
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ljmaq.budgetrule.domain.model.Partition
import com.ljmaq.budgetrule.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "BUDGET_RULE")

class DataStoreRepositoryImpl(context: Context) : DataStoreRepository {
    private object PreferencesKey {
        val balanceKey = doublePreferencesKey(name = "BALANCE")
        val excessPartitionAmountKey = doublePreferencesKey(name = "PARTITION_AMOUNT")
        val excessPartitionSharePercentKey = floatPreferencesKey(name = "PARTITION_SHARE_PERCENT")
        val onBoardingKey = booleanPreferencesKey(name = "ONBOARDING")
        val currencyKey = stringPreferencesKey(name = "CURRENCY")
    }

    private val dataStore = context.dataStore

    override suspend fun saveBalanceState(balance: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.balanceKey] = balance
        }
    }

    override suspend fun saveExcessPartitionState(amount: Double, @FloatRange(from = 0.0, to = 1.0) sharePercent: Float) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.excessPartitionAmountKey] = amount
            preferences[PreferencesKey.excessPartitionSharePercentKey] = sharePercent
        }
    }

    override fun readBalanceState(): Flow<Double> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val balanceState = preferences[PreferencesKey.balanceKey] ?: 0.0
                balanceState
            }
    }

    override fun readExcessPartitionState(): Flow<Partition> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val leftOverPartitionState = Partition(
                    name = "Excess",
                    amount = preferences[PreferencesKey.excessPartitionAmountKey] ?: 0.0,
                    sharePercent = preferences[PreferencesKey.excessPartitionSharePercentKey] ?: 1f
                )
                leftOverPartitionState
            }
    }

    override suspend fun saveOnBoardingState(showOnBoarding: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = showOnBoarding
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: true
                onBoardingState
            }
    }

    override suspend fun saveCurrencyState(currencyCode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.currencyKey] = currencyCode
        }
    }

    override fun readCurrencyState(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                val currencyState = preferences[PreferencesKey.currencyKey] ?: ""
                currencyState
            }
    }
}