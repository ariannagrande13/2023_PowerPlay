package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="First Op Mode")
public class firstOpMode extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private Servo servoOne;

    @Override
    public void runOpMode() {
        leftDrive  = hardwareMap.get(DcMotor.class, "leftMotor");
        rightDrive = hardwareMap.get(DcMotor.class, "rightMotor");
        servoOne = hardwareMap.get(Servo.class, "servoOne");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad1.a) {
                servoOne.setPosition(servoOne.getPosition() + 0.00015);
            } else if (gamepad1.b) {
                servoOne.setPosition(servoOne.getPosition() - 0.00015);

            }
            telemetry.addData("Servo pos", servoOne.getPosition());
            telemetry.update();
        }
    }

}


































































































































































































































