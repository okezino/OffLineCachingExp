package com.codinginflow.mvvmnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.codinginflow.mvvmnewsapp.databinding.ActivityMainBinding
import com.codinginflow.mvvmnewsapp.features.bookmark.BookMarkFragment
import com.codinginflow.mvvmnewsapp.features.breakingnews.BreakingNewsFragment
import com.codinginflow.mvvmnewsapp.features.search.SearchingFragment
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var breakingNewsFragment: BreakingNewsFragment
    private lateinit var searchingFragment: SearchingFragment
    private lateinit var bookMarkFragment: BookMarkFragment

    private val fragments : Array<Fragment>
    get() = arrayOf(
        breakingNewsFragment,
        searchingFragment,
        bookMarkFragment
    )

    private var selectedIndex = 0

    private val selectedFragment get() = fragments[selectedIndex]
   private fun selectFragment(selectedFragment : Fragment){
       var transaction = supportFragmentManager.beginTransaction()
       fragments.forEachIndexed{
           index, fragment ->
           if(selectedFragment == fragment){
               transaction = transaction.attach(fragment)
               selectedIndex = index
           }else {
               transaction = transaction.detach(fragment)
           }
       }
       transaction.commit()

       title = when(selectedFragment){
           is BreakingNewsFragment -> getString(R.string.title_breaking_news)
           is SearchingFragment -> getString(R.string.title_search_news)
           is BookMarkFragment -> getString(R.string.title_bookmarks)
           else -> ""

       }
   }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       if(savedInstanceState == null){
           breakingNewsFragment = BreakingNewsFragment()
           searchingFragment = SearchingFragment()
           bookMarkFragment = BookMarkFragment()
           supportFragmentManager.beginTransaction()
               .add(R.id.fragment_container, breakingNewsFragment, BREAKING_TAG)
               .add(R.id.fragment_container, searchingFragment, SEARCH_TAG)
               .add(R.id.fragment_container, bookMarkFragment, BOOKMARK_TAG)
               .commit()
       }else {
           breakingNewsFragment =
               supportFragmentManager.findFragmentByTag(BREAKING_TAG) as BreakingNewsFragment
           searchingFragment =
               supportFragmentManager.findFragmentByTag(SEARCH_TAG) as SearchingFragment
           bookMarkFragment =
               supportFragmentManager.findFragmentByTag(BOOKMARK_TAG) as BookMarkFragment

           selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)

       }
        selectFragment(selectedFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            val fragment = when(item.itemId){
                R.id.nav_breaking -> breakingNewsFragment
                R.id.nav_search -> searchingFragment
                R.id.nav_bookmarks -> bookMarkFragment
                else -> throw IllegalArgumentException("Unexpected ItemId")
            }
            selectFragment(fragment)

            /**
             * we return true because we re telling the
             * system that we will handle the item clicks
             */
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }

    override fun onBackPressed() {
        if(selectedIndex != 0){
            binding.bottomNav.selectedItemId =
            R.id.nav_breaking
        }else {
            super.onBackPressed()
        }
    }


}
private const val BREAKING_TAG = "breaking"
private const val SEARCH_TAG = "search"
private const val BOOKMARK_TAG = "bookmark"
private const val KEY_SELECTED_INDEX = "Selected index"