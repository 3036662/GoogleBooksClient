package ru.tusur.googlebooksclient.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse (
    @SerialName(value="kind") val kind:String,
    @SerialName(value="totalItems") val totalItems:Long,
    @SerialName(value = "items") val items:List<Volume>?=null
)

@Serializable
data class Volume (
    val kind: String?=null,
    val id: String?=null,
    val etag: String?=null,
    val selfLink: String?=null,
    val volumeInfo: VolumeInfo?=null,
    val saleInfo: SaleInfo?=null,
    val accessInfo: AccessInfo?=null,
    val searchInfo: SearchInfo?=null
)

@Serializable
data class VolumeInfo (
    val title: String?=null,
    val subtitle: String? = null,
    val authors: List<String>?=null,
    val publisher: String?=null,
    val publishedDate: String?=null,
    val description: String?=null,
    val industryIdentifiers: List<IndustryIdentifier>?=null,
    val readingModes: ReadingModes,
    val pageCount: Long?=null,
    val dimensions:Dimensions?=null,
    val printType: String?=null,
    val categories: List<String>?=null,
    val averageRating: Double? = null,
    val ratingsCount: Long? = null,
    val contentVersion: String?=null,
    val imageLinks: ImageLinks,
    val language: String?=null,
    val mainCategory:String?=null,
    val printedPageCount:Long?=null,
    val previewLink: String?=null,
    val userInfo:UserInfo?=null,
    val saleInfo: SaleInfo?=null,
    val accessInfo: AccessInfo?=null,
    val infoLink: String?=null,
    val canonicalVolumeLink: String?=null,
    val searchInfo:SearchInfo?=null,
    val maturityRating: String?=null,
    val allowAnonLogging: Boolean?=null,
    val layerInfo: LayerInfo?=null,

    val panelizationSummary: PanelizationSummary?=null,








)

@Serializable
data class UserInfo(
    val review:String?=null,
    val readingPosition:String?=null,
    val isPurchased:Boolean=false,
    val updated:String?=null,
    val isPreordered:Boolean?=null,

)

@Serializable
data class Dimensions(
    val height:String?=null,
    val width:String?=null,
    val thickness:String?=null
)

@Serializable
data class IndustryIdentifier (
    val type: String,
    val identifier: String
)
@Serializable
data class ReadingModes (
    val text: Boolean,
    val image: Boolean
)

@Serializable
data class PanelizationSummary (
    val containsEpubBubbles: Boolean,
    val containsImageBubbles: Boolean
)


@Serializable
data class ImageLinks (
    val smallThumbnail: String,
    val thumbnail: String,
    val small: String?=null,
    val medium: String?=null,
    val large: String?=null,
    val extraLarge: String?=null
)

@Serializable
data class SaleInfo (
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val listPrice: SaleInfoListPrice? = null,
    val retailPrice: SaleInfoListPrice? = null,
    val buyLink: String? = null,
    val offers: List<Offer>? = null,
    val onSaleDate:String?=null,

)




@Serializable
data class SaleInfoListPrice (
    val amount: Double,
    val currencyCode: String
)

@Serializable
data class Offer (
    val finskyOfferType: Long,
    val listPrice: OfferListPrice,
    val retailPrice: OfferListPrice
)
@Serializable
data class OfferListPrice (
    val amountInMicros: Long,
    val currencyCode: String
)

@Serializable
data class AccessInfo (
    val country: String,
    val viewability: String,
    val embeddable: Boolean,
    val publicDomain: Boolean,
    val textToSpeechPermission: String,
    val epub: FormatAvailable,
    val pdf: FormatAvailable,
    val webReaderLink: String,
    val accessViewStatus: String,
    val downloadAccess:DownloadAccess?=null,
    val quoteSharingAllowed: Boolean
)

@Serializable
data class DownloadAccess(
    val kind:String?=null,
    val volumeId:String?=null,
    val restricted:Boolean?=null,
    val deviceAllowed:Boolean?=null,
    val justAcquired:Boolean?=null,
    val maxDownloadDevices:Int?=null,
    val downloadsAcquired:Int?=null,
    val nonce:String?=null,
    val source:String?=null,
    val reasonCode:String?=null,
    val message:String?=null,
    val signature:String?=null,

)


@Serializable
data class FormatAvailable (
    val isAvailable: Boolean,
    val acsTokenLink: String? = null,
    val downloadLink:String?=null
)

@Serializable
data class SearchInfo (
    val textSnippet: String
)

/// books full description
@Serializable
data class BookByIdResponse (
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val layerInfo: LayerInfo?=null,
    val saleInfo: SaleInfo,
    val accessInfo: AccessInfo
)


@Serializable
data class LayerInfo (
    val layers: List<Layer>
)
@Serializable
data class Layer (
    val layerId: String,
    val volumeAnnotationsVersion: String
)

