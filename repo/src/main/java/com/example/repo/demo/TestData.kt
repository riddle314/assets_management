package com.example.repo.demo

import com.example.repo.model.*

class TestData {
    companion object {

        fun getTranscationsTestData(): ArrayList<TransactionItemResponseDataModel> {
            val transactions = ArrayList<TransactionItemResponseDataModel>()
            transactions.add(
                TransactionItemResponseDataModel(
                    1,
                    "assetName1",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    2,
                    "assetName2",
                    0.023,
                    21.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    3,
                    "assetName3",
                    0.023,
                    22.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    4,
                    "assetName4",
                    0.023,
                    25.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    5,
                    "assetName5",
                    0.023,
                    29.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    6,
                    "assetName6",
                    0.005,
                    26.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    7,
                    "assetName7",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.STOCK,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    8,
                    "assetName8",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    9,
                    "assetName9",
                    0.023,
                    30.5,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CURRENCY,
                    TransactionTypeData.SELL
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    1,
                    "assetName1",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    2,
                    "assetName2",
                    0.023,
                    21.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    3,
                    "assetName3",
                    0.023,
                    22.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    4,
                    "assetName4",
                    0.023,
                    25.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    5,
                    "assetName5",
                    0.023,
                    29.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    6,
                    "assetName6",
                    0.005,
                    26.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    7,
                    "assetName7",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.STOCK,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    8,
                    "assetName8",
                    0.023,
                    20.0,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CRYPTO,
                    TransactionTypeData.BUY
                )
            )
            transactions.add(
                TransactionItemResponseDataModel(
                    9,
                    "assetName9",
                    0.023,
                    30.5,
                    "EUR",
                    1638120473231,
                    AssetTypeData.CURRENCY,
                    TransactionTypeData.SELL
                )
            )
            return transactions
        }

        fun getTransactionsTestDataForSearchQuery(query: String): List<TransactionItemResponseDataModel> {
            return searchListOfTransactionItemResponseDataModel(query, getTranscationsTestData())
        }

        fun getTransactionDetailsTestData(transactionId: Int): TransactionDetailsResponseDataModel {
            val data = getTranscationsTestData()
            if (!data.isNullOrEmpty()) {
                for (transactionItem in data) {
                    if (transactionId.equals(transactionItem.transactionId)) {
                        return TransactionDetailsResponseDataModel(
                            transactionItem.transactionId,
                            transactionItem.assetsName,
                            transactionItem.quantity,
                            transactionItem.price,
                            transactionItem.priceCurrency,
                            transactionItem.date,
                            transactionItem.assetType,
                            transactionItem.transactionType
                        )
                    }
                }
            }
            return TransactionDetailsResponseDataModel(
                0,
                "",
                0.0,
                0.0,
                "",
                0,
                AssetTypeData.CRYPTO,
                TransactionTypeData.BUY
            )
        }

        fun getCurrenciesTestData(): ArrayList<SelectionListResultDataModel> {
            val target = ArrayList<SelectionListResultDataModel>()
            target.add(SelectionListResultDataModel("EUR", "Euro"))
            target.add(SelectionListResultDataModel("DOL", "American Dollar"))
            target.add(SelectionListResultDataModel("AUD", "Australian Dollar"))
            target.add(SelectionListResultDataModel("GBP", "Pound"))
            return target
        }

        fun getCurrenciesTestDataForSearchQuery(query: String): ArrayList<SelectionListResultDataModel> {
            return searchListOfSelectionListResultDataModel(query, getCurrenciesTestData())
        }

        fun getCryptoTestData(): ArrayList<SelectionListResultDataModel> {
            val target = ArrayList<SelectionListResultDataModel>()
            target.add(SelectionListResultDataModel("BTC", "Bitcoin"))
            target.add(SelectionListResultDataModel("ETH", "Ethereum"))
            target.add(SelectionListResultDataModel("XRP", "Ripple"))
            target.add(SelectionListResultDataModel("LTC", "Litecoin"))
            return target
        }

        fun getCryptoTestDataForSearchQuery(query: String): ArrayList<SelectionListResultDataModel> {
            return searchListOfSelectionListResultDataModel(query, getCryptoTestData())
        }

        fun getStocksTestData(): ArrayList<SelectionListResultDataModel> {
            val target = ArrayList<SelectionListResultDataModel>()
            target.add(SelectionListResultDataModel("AAPL", "Apple"))
            target.add(SelectionListResultDataModel("GOOGL", "Google"))
            target.add(SelectionListResultDataModel("MSFT", "Microsoft"))
            target.add(SelectionListResultDataModel("TSLA", "Tesla"))
            return target
        }

        fun getStocksTestDataForSearchQuery(query: String): ArrayList<SelectionListResultDataModel> {
            return searchListOfSelectionListResultDataModel(query, getStocksTestData())
        }

        private fun searchListOfSelectionListResultDataModel(
            query: String,
            source: List<SelectionListResultDataModel>
        ): ArrayList<SelectionListResultDataModel> {
            val target = ArrayList<SelectionListResultDataModel>()
            for (item in source) {
                if (item.name.contains(query, true)
                    || item.description.contains(query, true)
                ) {
                    target.add(item)
                }
            }
            return target
        }


        private fun searchListOfTransactionItemResponseDataModel(
            query: String,
            source: List<TransactionItemResponseDataModel>
        ): List<TransactionItemResponseDataModel> {
            val target = ArrayList<TransactionItemResponseDataModel>()
            for (item in source) {
                if (item.assetsName.contains(query, true)
                    || item.quantity.toString().contains(query, true)
                    || item.price.toString().contains(query, true)
                    || item.priceCurrency.contains(query, true)
                ) {
                    target.add(item)
                }
            }
            return target
        }
    }
}