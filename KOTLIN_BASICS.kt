import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main (){
    var smartDevice: SmartDevices = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()
    
    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
}
// DRY defining set conditions by using delegates (abstraction)
class RangeRegulator(
          initialValue: Int,
          private val minValue: Int,
          private val maxValue: Int
) : ReadWriteProperty<Any?, Int>{
     var fieldData = initialValue
    
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        
            if(value in minValue..maxValue){
                fieldData = value
            
        }
    }
}

// Smart device class to contain every IOT device
open class SmartDevices(val name: String, val category: String){
    
    
    var deviceStatus = "online"
    protected set
    
    open val deviceType = "unknown"
   
    open fun turnOn(){
         deviceStatus = "on"
         }
    
    open fun turnOff(){
         deviceStatus = "off"
        }
 
}
// TV class
class SmartTvDevice(deviceName: String, deviceCategory: String): SmartDevices(name = deviceName, category = deviceCategory){
    
    override val deviceType = "Smart Tv"

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)
    
   private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
   
    fun increaseSpeakerVolume(){
        speakerVolume++
        println("Volume is now at $speakerVolume")
    }
    
    fun nextChannel(){
        channelNumber++
        println("$channelNumber channel number")
    }
    
       override fun turnOn(){
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                "set to $channelNumber.")
    }
    
    override fun turnOff(){
         super.turnOff()
        println("$name turned off")
    }
      
}

// Light class 
class SmartLightDevice(deviceName: String, deviceCategory: String): SmartDevices(name = deviceName, category = deviceCategory){
    override val deviceType = "Smart Light"
    
    private var brightnessLevel by RangeRegulator(initialValue = 50, minValue = 0, maxValue = 100)
    
    
    fun increaseBrightness(){
        brightnessLevel++
        println("brightness level increased to $brightnessLevel")
    }    
    
   override fun turnOn(){
         super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.") 
    }
    
    override fun turnOff(){
         super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

// Smart home that has-a relation
class SmartHome(
    val smartTvdevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
)
{
    var deviceTurnOnCount = 1
    private set
    
    fun turnOnTv(){
        smartTvdevice.turnOn()
        deviceTurnOnCount++
    }
    
    fun turnOffTv(){
        smartTvdevice.turnOff()
        deviceTurnOnCount--
    }
    
    fun increaseTvVolume(){
        smartTvdevice.increaseSpeakerVolume()
    }
    
    fun decreaseTvVolume(){
        smartTvdevice.nextChannel()
    }
    
    fun turnOnLightDevice(){
        smartLightDevice.turnOn()
         deviceTurnOnCount++
    }
    
    fun turnOffLightDevice(){
        smartLightDevice.turnOff()
        deviceTurnOnCount--
    }
    
    fun increaseLight(){
        smartLightDevice.increaseBrightness()
    }
    
    fun turnOffAllDevices(){
        turnOffTv()
        turnOffLightDevice()
    }
}
