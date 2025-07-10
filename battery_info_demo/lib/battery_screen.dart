import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BatteryScreen extends StatefulWidget {
  const BatteryScreen({super.key});

  @override
  State<BatteryScreen> createState() => _BatteryScreenState();
}

class _BatteryScreenState extends State<BatteryScreen> {
  static const platform = MethodChannel('com.example.battery');
  String _batteryLevel = 'Unknown';

  Future<void> _getBatteryLevel() async {
    try{
      final int result = await platform.invokeMethod('getBatteryLevel');
      setState(() => _batteryLevel = 'Battery leve: $result%');
    } on PlatformException catch (e){
      setState(() {
        _batteryLevel = 'Failed to get: ${e.message}';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('电池')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          spacing: 8,
          children: [
            Text(_batteryLevel),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _getBatteryLevel, 
              child: const Text('获取')
            )
          ],
        ),
      ),
    );
  }
}