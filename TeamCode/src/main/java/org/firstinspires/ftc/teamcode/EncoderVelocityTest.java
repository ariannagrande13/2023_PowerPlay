package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Arrays;
import java.util.List;

@TeleOp(group = "drive")
public class EncoderVelocityTest extends LinearOpMode {
    private FtcDashboard dashboard = FtcDashboard.getInstance();

    public void runOpMode() throws InterruptedException {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
        Encoder leftEncoder, rightEncoder, frontEncoder;

        DcMotorEx leftFront, leftRear, rightRear, rightFront;

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftBack"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightBack"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

        // TODO: reverse any encoders using Encoder.setDirection(Encoder.Direction.REVERSE)
        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        frontEncoder.setDirection(Encoder.Direction.REVERSE);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            telemetry.addData("left encoder position", leftEncoder.getCurrentPosition());
            telemetry.addData("left encoder velocity", leftEncoder.getCorrectedVelocity());
            telemetry.addData("right encoder position", rightEncoder.getCurrentPosition());
            telemetry.addData("right encoder velocity", rightEncoder.getCorrectedVelocity());
            telemetry.addData("vertical encoder position", frontEncoder.getCurrentPosition());
            telemetry.addData("vertical encoder velocity", frontEncoder.getCorrectedVelocity());
            telemetry.update();
        }

    }
}
