package com.mshabiola.database.di

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.Security
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import timber.log.Timber

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier(name)) {
            val driver = AndroidSqliteDriver(
                schema = SeriesDatabase.Schema,
                context = get(),
                factory = SQLiteCopyOpenHelper.Factory(
                    context = get(),
                    delegate = FrameworkSQLiteOpenHelperFactory(),
                    copyConfig = CopyFromAssetPath(Security.assetData)
                ),

                name = Security.databaseName,
                callback = object : AndroidSqliteDriver.Callback(SeriesDatabase.Schema) {
                    //                    override fun onOpen(db: SupportSQLiteDatabase) {
//                        super.onOpen(db)
//                        db.setForeignKeyConstraintsEnabled(true)
//                    }
                    override fun onUpgrade(
                        db: SupportSQLiteDatabase,
                        oldVersion: Int,
                        newVersion: Int,
                    ) {
                        Timber.e("upgrade $oldVersion, to $newVersion")

                    }

                    override fun onDowngrade(
                        db: SupportSQLiteDatabase,
                        oldVersion: Int,
                        newVersion: Int,
                    ) {
                        Timber.e("downgrade $oldVersion, to $newVersion")

                    }
                }
            )



            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }
        single(qualifier = qualifier("temp")) {
            val driver = AndroidSqliteDriver(
                schema = SeriesDatabase.Schema,
                context = get(),
                name = null,
//                callback = object : AndroidSqliteDriver.Callback(SeriesDatabase.Schema) {
//                    override fun onOpen(db: SupportSQLiteDatabase) {
//                        super.onOpen(db)
//                        db.setForeignKeyConstraintsEnabled(true)
//                    }
//                }
            )

            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }

        includes(daoModules)
    }