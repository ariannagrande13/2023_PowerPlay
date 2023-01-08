package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp(name = "Power Play Tele Op")
public class PiRatesTeleOp extends LinearOpMode {

    private DcMotor leftFront, rightFront, leftBack, rightBack, lifterMotor;
    private Servo clawOne, clawTwo;
    private TouchSensor coneTouch, lifterSensor;
    private DistanceSensor sideDistance, frontDistance, poleSensor;

    private final double CLAW_ONE_OPEN = 0.45;
    private final double CLAW_ONE_CLOSE = 0.70;

    private final double CLAW_TWO_OPEN = 0.55;
    private final double CLAW_TWO_CLOSE = 0.30;

    double lifterPower;

    @Override
    public void runOpMode() {
        initHardwareMap();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive() || !isStopRequested()) {
                mecanumDrive();
                coneGrabber();
                lifterPower = gamepad2.right_stick_y;
                if (gamepad2.right_stick_y < 0) {
                    if (lifterMotor.getCurrentPosition() <= -6900) {
                        lifterMotor.setPower(0);
                    } else {
                        lifterMotor.setPower(lifterPower);
                    }
                } else if (gamepad2.right_stick_y > 0) {
                    lifterMotor.setPower(lifterPower);
                    clawOne.setPosition(CLAW_ONE_CLOSE);
                    clawTwo.setPosition(CLAW_TWO_CLOSE);

                } else {
                    lifterMotor.setPower(0);
                }
                telemetry.addData("liftermotor pos", lifterMotor.getCurrentPosition());
                telemetry.update();
            }
        }

    }
    public void initHardwareMap() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        lifterMotor = hardwareMap.dcMotor.get("lifterMotor");

        clawOne = hardwareMap.servo.get("clawOne");
        clawTwo = hardwareMap.servo.get("clawTwo");

        coneTouch = hardwareMap.touchSensor.get("coneTouch");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lifterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lifterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lifterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void mecanumDrive() {
        double r = Math.hypot(gamepad1.left_stick_x * -1, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x * -1) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x * -1;

        double lFront = r * Math.cos(robotAngle) + rightX;
        double rFront = r * Math.sin(robotAngle) - rightX;
        double lRear = r * Math.sin(robotAngle) + rightX;
        double rRear = r * Math.cos(robotAngle) - rightX;
        leftFront.setPower(lFront);
        rightFront.setPower(rFront);
        leftBack.setPower(lRear);
        rightBack.setPower(rRear);

        // Turn functionality with the gamepad triggers
        double strafe_left_power = gamepad1.left_trigger;
        double strafe_right_power = gamepad1.right_trigger;

        if (gamepad1.left_trigger > 0) {
            leftFront.setPower(strafe_left_power);
            rightFront.setPower(-strafe_left_power);
            leftBack.setPower(-strafe_left_power);
            rightBack.setPower(strafe_left_power);
        }
        if (gamepad1.right_trigger > 0) {
            leftFront.setPower(-strafe_right_power);
            rightFront.setPower(strafe_right_power);
            leftBack.setPower(strafe_right_power);
            rightBack.setPower(-strafe_right_power);
        }
    }
    public void coneGrabber() {
        if (gamepad2.a) {
            clawOne.setPosition(CLAW_ONE_OPEN);
            clawTwo.setPosition(CLAW_TWO_OPEN);
        }
        if (gamepad2.b) {
            clawOne.setPosition(CLAW_ONE_CLOSE);
            clawTwo.setPosition(CLAW_TWO_CLOSE);
        }
        // If touch sensor on grabber pressed, it knows there is a cone there
        // Close the grabber
        if (coneTouch.isPressed() == true) {
            clawOne.setPosition(CLAW_ONE_CLOSE);
            clawTwo.setPosition(CLAW_TWO_CLOSE);
        }
    }
}
