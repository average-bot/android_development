import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.analysis.AnalysisFragment
import com.example.myapplication.home.HomeFragment
import com.example.myapplication.questions.QuestionsFragment


class ParentFragmentPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> QuestionsFragment()
            else -> AnalysisFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}