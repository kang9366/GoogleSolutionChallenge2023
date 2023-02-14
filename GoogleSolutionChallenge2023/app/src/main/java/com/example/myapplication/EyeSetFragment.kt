import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.EyeStretchingStart
import com.example.myapplication.NeckStretchingStart
import com.example.myapplication.databinding.FragmentEyeSetBinding

class EyeSetFragment : Fragment() {
    private var _binding: FragmentEyeSetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentEyeSetBinding.inflate(inflater, container, false)
        binding.eyeStrechingStart.setOnClickListener {
            val intent = Intent(activity, EyeStretchingStart::class.java)
            startActivity(intent)
        }
        return binding.root
    }

}