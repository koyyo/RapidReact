// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

import java.beans.IntrospectionException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final Spark shooter = new Spark(RobotMap.shooter);
  private final Spark intake = new Spark(RobotMap.intake);
  
  private final TalonSRX fl = new TalonSRX(RobotMap.frontLeft);
  private final TalonSRX fr = new TalonSRX(RobotMap.frontRight);
  private final TalonSRX bl = new TalonSRX(RobotMap.backLeft);
  private final TalonSRX br = new TalonSRX(RobotMap.backRight);

  private final Joystick joy = new Joystick(0);

  private final Compressor comp = new Compressor(RobotMap.compressor, PneumaticsModuleType.CTREPCM);
  private final DoubleSolenoid paddle = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, RobotMap.shootup, RobotMap.shootdown);

  private final float SPD = 1f;
  private final float sideSPD = 0.5f;
  private final float shootSPD = 0.5f;
  private final float intakeSPD = 0.6f;

  //private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    /*
    m_timer.reset();
    m_timer.start();
    */
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    /*
    if (m_timer.get() < 2.0) {
      m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
    */
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    fr.set(ControlMode.PercentOutput, SPD * (joy.getRawAxis(5) + sideSPD * joy.getRawAxis(4)));
    br.set(ControlMode.PercentOutput, SPD * (joy.getRawAxis(5) - sideSPD * joy.getRawAxis(4)));
    fl.set(ControlMode.PercentOutput, SPD * (-joy.getRawAxis(1) + sideSPD * joy.getRawAxis(0)));
    bl.set(ControlMode.PercentOutput, SPD * (-joy.getRawAxis(1) - sideSPD * joy.getRawAxis(0)));

    shooter.set(shootSPD * joy.getRawAxis(3));
    
    intake.set(intakeSPD * joy.getRawAxis(2));

    if (joy.getRawButton(6))
      paddle.set(Value.kForward);
    else
      paddle.set(Value.kReverse);
    
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}