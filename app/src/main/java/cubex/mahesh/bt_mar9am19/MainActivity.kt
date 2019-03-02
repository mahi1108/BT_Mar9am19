package cubex.mahesh.bt_mar9am19

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bAdapter:BluetoothAdapter =
                            BluetoothAdapter.getDefaultAdapter()
        //1
        s1.isChecked = bAdapter.isEnabled
        //2
        s1.setOnCheckedChangeListener {
                compoundButton, b ->
            if(b)
                bAdapter.enable()
            else
                bAdapter.disable()
        }
        //3
        getBtDevices.setOnClickListener {
            var templist = mutableListOf<String>()
            var lview_adapter = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_list_item_single_choice,
                templist)
            lview.adapter = lview_adapter
            bAdapter.startDiscovery()
            var iFilter = IntentFilter()
            iFilter.addAction(BluetoothDevice.ACTION_FOUND)
            registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                        var device = p1?.getParcelableExtra<BluetoothDevice>(
                            BluetoothDevice.EXTRA_DEVICE)
                    templist.add(device?.name+"\n"+device?.address)
                    lview_adapter.notifyDataSetChanged()
                }
            },iFilter)

        }
    }
}
