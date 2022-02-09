# IP Map

This is a map of what device corresponds to what IP address, try to keep this consistent so we can keep them constant.

The mDNS addresses (`<hostname>.local`) _can_ be used, but they are apparently unreliable on the field, so we will use static IPs for tasks requiring interfacing between two devices.

All IP addresses start with `10.8.68`.

| Device       | Hostname      | mDNS address            | IP address                              |
| ------------ | ------------- | ----------------------- | --------------------------------------- |
| Radio        | TBD           | N/A                     | `10.8.68.1`                             |
| roboRIO      | TBD           | `roborio-868-frc.local` | `10.8.68.2`                             |
| Raspberry Pi | `raspberrypi` | `raspberrypi.local`     | `10.8.68.150`                           |
| Limelight    | `limelight`   | `limelight.local`       | `10.8.68.151` (previously `10.8.68.68`) |
