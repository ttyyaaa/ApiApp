package jp.techacademy.touya.kobayashi.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_web_view.*
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_api.*

class WebViewActivity: AppCompatActivity() {

    private var favoriteShop: FavoriteShop = FavoriteShop()
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        //webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
        favoriteShop = intent.getSerializableExtra(FAVORITE_SHOP) as? FavoriteShop  ?: return { finish() }()
        webView.loadUrl(favoriteShop.url)
        isFavorite = FavoriteShop.findBy(favoriteShop.id) != null
        favoriteImageView.apply {
            setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border) // Picassoというライブラリを使ってImageVIewに画像をはめ込む
        }

        //★ここから追記
        favoriteImageView.setOnClickListener {
            isFavorite = FavoriteShop.findBy(favoriteShop.id) != null
            if (isFavorite) {
                FavoriteShop.delete(favoriteShop.id)
                favoriteImageView.apply { setImageResource(R.drawable.ic_star_border) }
            } else {
                FavoriteShop.insert(favoriteShop)
                isFavorite = FavoriteShop.findBy(favoriteShop.id) != null
                favoriteImageView.apply { setImageResource(R.drawable.ic_star) }
            }
        }

    }

    companion object {
        private const val KEY_URL = "key_url"
        private const val FAVORITE_SHOP = "key_url"
        fun start(activity: Activity, url: String) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, url))
        }
        fun start(activity: Activity, favoriteShop: FavoriteShop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(FAVORITE_SHOP, favoriteShop))
        }
    }

}